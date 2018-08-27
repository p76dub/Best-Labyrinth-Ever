package util.message;

import util.agent.IAgent;

public interface IMessage {
    IAgent getSender();
    IAgent getReceiver();
    Object getContent();
}
