package org.camunda.tngp.transport.impl;

import org.agrona.DirectBuffer;
import org.camunda.tngp.transport.TransportChannel;
import org.camunda.tngp.transport.protocol.Protocols;
import org.camunda.tngp.transport.protocol.TransportHeaderDescriptor;
import org.camunda.tngp.transport.spi.TransportChannelHandler;

public class CompositeChannelHandler implements TransportChannelHandler
{

    protected TransportChannelHandler[] handlers = Protocols.handlerForAllProtocols(new DefaultChannelHandler());

    /**
     * Handlers must be registered according to the protocol IDs defined in {@link Protocols}.
     */
    public void addHandler(short protocolId, TransportChannelHandler handler)
    {
        handlers[protocolId] = handler;
    }

    @Override
    public void onChannelOpened(TransportChannel transportChannel)
    {
        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onChannelOpened(transportChannel);
        }
    }

    @Override
    public void onChannelClosed(TransportChannel transportChannel)
    {
        for (int i = 0; i < handlers.length; i++)
        {
            handlers[i].onChannelClosed(transportChannel);
        }
    }

    @Override
    public void onChannelSendError(TransportChannel transportChannel, DirectBuffer buffer, int offset, int length)
    {
        final TransportChannelHandler handler = getHandler(buffer, offset, length);
        handler.onChannelSendError(transportChannel, buffer, offset, length);
    }

    @Override
    public boolean onChannelReceive(TransportChannel transportChannel, DirectBuffer buffer, int offset, int length)
    {
        final TransportChannelHandler handler = getHandler(buffer, offset, length);
        return handler.onChannelReceive(transportChannel, buffer, offset, length);
    }

    @Override
    public void onControlFrame(TransportChannel transportChannel, DirectBuffer buffer, int offset, int length)
    {
        final TransportChannelHandler handler = getHandler(buffer, offset, length);
        handler.onControlFrame(transportChannel, buffer, offset, length);
    }

    protected TransportChannelHandler getHandler(DirectBuffer message, int protocolHeaderOffset, int length)
    {
        final short protocolId = message.getShort(TransportHeaderDescriptor.protocolIdOffset(protocolHeaderOffset));

        if (protocolId >= handlers.length)
        {
            throw new RuntimeException("Invalid protocol id " + protocolId);
        }

        return handlers[protocolId];
    }
}
