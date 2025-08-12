package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class Elf64Header extends Elf.Header {
    private final ElfParser parser;

    public Elf64Header(final boolean bigEndian, final ElfParser parser) throws IOException {
        this.bigEndian = bigEndian;
        this.parser = parser;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = parser.readHalf(buffer, 16L);
        this.phoff = parser.readLong(buffer, 32L);
        this.shoff = parser.readLong(buffer, 40L);
        this.phentsize = parser.readHalf(buffer, 54L);
        this.phnum = parser.readHalf(buffer, 56L);
        this.shentsize = parser.readHalf(buffer, 58L);
        this.shnum = parser.readHalf(buffer, 60L);
        this.shstrndx = parser.readHalf(buffer, 62L);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(final int index) throws IOException {
        return new Section64Header(this.parser, this, index);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(final long index) throws IOException {
        return new Program64Header(this.parser, this, index);
    }

    @Override // com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(final long baseOffset, final int index) throws IOException {
        return new Dynamic64Structure(this.parser, this, baseOffset, index);
    }
}
