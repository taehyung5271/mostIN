package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Dynamic64Structure extends Elf.DynamicStructure {
    public Dynamic64Structure(final ElfParser parser, final Elf.Header header, long baseOffset, final int index) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long baseOffset2 = baseOffset + (index * 16);
        this.tag = parser.readLong(buffer, baseOffset2);
        this.val = parser.readLong(buffer, 8 + baseOffset2);
    }
}
