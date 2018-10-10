package com.jclarity.safepoint.event;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class JVMEventCodec implements MessageCodec<JVMEvent,JVMEvent> {
    @Override
    public void encodeToWire(Buffer buffer, JVMEvent jvmEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JVMEvent decodeFromWire(int i, Buffer buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JVMEvent transform(JVMEvent jvmEvent) {
        return jvmEvent;
    }

    @Override
    public String name() {
        return "JVMEvent";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
