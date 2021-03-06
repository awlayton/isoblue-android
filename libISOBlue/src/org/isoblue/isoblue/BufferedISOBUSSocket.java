package org.isoblue.isoblue;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.ClosedChannelException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.isoblue.isobus.ISOBUSSocket;
import org.isoblue.isobus.Message;
import org.isoblue.isobus.NAME;
import org.isoblue.isobus.PGN;

public class BufferedISOBUSSocket extends ISOBUSSocket {

    private final Serializable mFromId, mToId;

    protected BufferedISOBUSSocket(Serializable fromId, Serializable toId,
            ISOBlueBus bus, NAME name, Collection<PGN> pgns) throws IOException {
        super(bus, name, pgns);

        mFromId = fromId;
        mToId = toId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.isoblue.isobus.ISOBUSSocket#connect()
     */
    @Override
    protected boolean connect() {
        return ((ISOBlueBus) getBus()).attach(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.isoblue.isobus.ISOBUSSocket#read()
     */
    @Override
    public Message read() throws InterruptedException, IOException {
        Message message;

        message = super.read();

        if (message == ISOBlueBus.MESSAGE_NONE) {
            throw new ClosedChannelException();
        }

        return message;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.isoblue.isobus.ISOBUSSocket#read(long,
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public Message read(long timeout, TimeUnit unit)
            throws InterruptedException, IOException {
        Message message;

        message = super.read(timeout, unit);

        if (message == ISOBlueBus.MESSAGE_NONE) {
            throw new ClosedChannelException();
        }

        return message;
    }

    /**
     * @return the {@link Message} ID after which this
     *         {@link BufferedISOBUSSocket}'s data begins.
     */
    public Serializable getFromId() {
        return mFromId;
    }

    /**
     * @return the {@link Message} ID after which this
     *         {@link BufferedISOBUSSocket}'s data stops.
     */
    public Serializable getToId() {
        return mToId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.isoblue.isobus.ISOBUSSocket#write(org.isoblue.isobus.Message)
     */
    @Override
    public void write(Message message) throws InterruptedException {
        // Buffered sockets are read only
        throw new UnsupportedOperationException(
                "Cannot write to a buffered ISOBUSSocket");
    }
}
