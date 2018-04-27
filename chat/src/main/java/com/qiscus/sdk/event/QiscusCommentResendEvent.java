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

package com.qiscus.sdk.event;

import com.qiscus.sdk.data.model.QiscusComment;

/**
 * Created on : August 25, 2017
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class QiscusCommentResendEvent {
    private QiscusComment qiscusComment;
    private Event event;

    public QiscusCommentResendEvent(QiscusComment qiscusComment, Event event) {
        this.qiscusComment = qiscusComment;
        this.event = event;
    }

    public QiscusComment getQiscusComment() {
        return qiscusComment;
    }

    public Event getEvent() {
        return event;
    }

    public enum Event {
        SENDING, SENT, FAILED
    }
}
