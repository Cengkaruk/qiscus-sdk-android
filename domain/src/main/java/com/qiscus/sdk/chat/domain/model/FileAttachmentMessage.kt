/*
 * Copyright (c) 2016 Qiscus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qiscus.sdk.chat.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.qiscus.sdk.chat.domain.util.*
import java.io.File
import java.util.*

/**
 * Created on : September 26, 2017
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
class FileAttachmentMessage(
        messageId: MessageId,
        var file: File?,
        val caption: String,
        defaultMessage: String,
        sender: User,
        date: Date,
        room: Room,
        state: MessageState,
        type: MessageType) : Message(messageId, defaultMessage, sender, date, room, state, type) {

    private constructor(parcel: Parcel) : this(
            parcel.readParcelable(MessageId::class.java.classLoader),
            parcel.readFile(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader),
            parcel.readDate()!!,
            parcel.readParcelable(Room::class.java.classLoader),
            parcel.readEnum<MessageState>()!!,
            parcel.readParcelable(MessageType::class.java.classLoader))

    val attachmentUrl by lazy {
        var url = ""
        if (type.payload.has("url")) {
            url = type.payload.optString("url")
        }
        if (url.isBlank() && text.startsWith("[file]") && text.endsWith("[/file]")) {
            url = text.replace("[file]", "")
                    .replace("[/file]", "")
                    .trim()
        }
        return@lazy url
    }

    val attachmentName by lazy {
        var name = attachmentUrl.getAttachmentName()
        if (name.isBlank() && file != null) {
            name = file!!.name
        }
        return@lazy name
    }

    override fun toString(): String {
        return "FileAttachmentMessage(messageId=$messageId, text='$text', sender=$sender, date=$date, " +
                "room=$room, state=$state, type=$type, file=$file, caption='$caption', " +
                "attachmentUrl='$attachmentUrl')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FileAttachmentMessage) return false
        if (!super.equals(other)) return false

        if (file != other.file) return false
        if (attachmentUrl != other.attachmentUrl) return false

        return true
    }

    override fun hashCode(): Int {
        return messageId.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(messageId, flags)
        parcel.writeFile(file)
        parcel.writeString(caption)
        parcel.writeString(text)
        parcel.writeParcelable(sender, flags)
        parcel.writeDate(date)
        parcel.writeParcelable(room, flags)
        parcel.writeEnum(state)
        parcel.writeParcelable(type, flags)
    }

    companion object CREATOR : Parcelable.Creator<FileAttachmentMessage> {
        override fun createFromParcel(parcel: Parcel): FileAttachmentMessage {
            return FileAttachmentMessage(parcel)
        }

        override fun newArray(size: Int): Array<FileAttachmentMessage?> {
            return arrayOfNulls(size)
        }
    }
}