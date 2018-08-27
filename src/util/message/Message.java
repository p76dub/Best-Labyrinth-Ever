package util.message;

import util.agent.IAgent;

public class Message implements IMessage {
    // ATTRIBUTS
    private final IAgent sender;
    private final IAgent receiver;
    private final Object content;

    // CONSTRUCTEUR
    public Message(IAgent sender, IAgent receiver, Object content) {
        if (sender == null || receiver == null || content == null) {
            throw new NullPointerException();
        }
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public IAgent getSender() {
        return sender;
    }

    @Override
    public IAgent getReceiver() {
        return receiver;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
