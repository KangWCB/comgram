package KangWCB.comgram.message.repository;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.message.Message;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> findReceiverMessage(Member member);
}
