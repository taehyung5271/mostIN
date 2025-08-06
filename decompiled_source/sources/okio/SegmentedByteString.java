package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okio.internal._SegmentedByteStringKt;

/* compiled from: SegmentedByteString.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010\u0005\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016J\u0015\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\r\u0010 \u001a\u00020\u0015H\u0010¢\u0006\u0002\b!J\b\u0010\"\u001a\u00020\u0015H\u0016J\b\u0010#\u001a\u00020\u0010H\u0016J\u001d\u0010$\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\u0001H\u0010¢\u0006\u0002\b&J\u0018\u0010'\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0015H\u0016J\r\u0010)\u001a\u00020\u0004H\u0010¢\u0006\u0002\b*J\u0015\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0015H\u0010¢\u0006\u0002\b.J\u0018\u0010/\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0015H\u0016J(\u00100\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u00101\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016J(\u00100\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016J\u0010\u00102\u001a\u00020\u00102\u0006\u00103\u001a\u000204H\u0016J\u0018\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u0015H\u0016J\b\u00108\u001a\u00020\u0001H\u0016J\b\u00109\u001a\u00020\u0001H\u0016J\b\u0010:\u001a\u00020\u0004H\u0016J\b\u0010;\u001a\u00020\u0001H\u0002J\b\u0010<\u001a\u00020\u0010H\u0016J\u0010\u0010=\u001a\u00020\u00132\u0006\u0010>\u001a\u00020?H\u0016J%\u0010=\u001a\u00020\u00132\u0006\u0010@\u001a\u00020A2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0010¢\u0006\u0002\bBJ\b\u0010C\u001a\u00020DH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006E"}, d2 = {"Lokio/SegmentedByteString;", "Lokio/ByteString;", "segments", "", "", "directory", "", "([[B[I)V", "getDirectory$okio", "()[I", "getSegments$okio", "()[[B", "[[B", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "", "base64Url", "copyInto", "", TypedValues.CycleType.S_WAVE_OFFSET, "", TypedValues.AttributesType.S_TARGET, "targetOffset", "byteCount", "digest", "algorithm", "digest$okio", "equals", "", "other", "", "getSize", "getSize$okio", "hashCode", "hex", "hmac", "key", "hmac$okio", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "", "pos", "internalGet$okio", "lastIndexOf", "rangeEquals", "otherOffset", TypedValues.Custom.S_STRING, "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toByteString", "toString", "write", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeReplace", "Ljava/lang/Object;", "okio"}, k = 1, mv = {1, 5, 1}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes2.dex */
public final class SegmentedByteString extends ByteString {
    private final transient int[] directory;
    private final transient byte[][] segments;

    /* renamed from: getSegments$okio, reason: from getter */
    public final byte[][] getSegments() {
        return this.segments;
    }

    /* renamed from: getDirectory$okio, reason: from getter */
    public final int[] getDirectory() {
        return this.directory;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SegmentedByteString(byte[][] segments, int[] directory) {
        super(ByteString.EMPTY.getData());
        Intrinsics.checkNotNullParameter(segments, "segments");
        Intrinsics.checkNotNullParameter(directory, "directory");
        this.segments = segments;
        this.directory = directory;
    }

    @Override // okio.ByteString
    public String string(Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "charset");
        return toByteString().string(charset);
    }

    @Override // okio.ByteString
    public String base64() {
        return toByteString().base64();
    }

    @Override // okio.ByteString
    public String hex() {
        return toByteString().hex();
    }

    @Override // okio.ByteString
    public ByteString toAsciiLowercase() {
        return toByteString().toAsciiLowercase();
    }

    @Override // okio.ByteString
    public ByteString toAsciiUppercase() {
        return toByteString().toAsciiUppercase();
    }

    @Override // okio.ByteString
    public ByteString digest$okio(String algorithm) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        MessageDigest $this$digest_u24lambda_u2d1 = MessageDigest.getInstance(algorithm);
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            $this$digest_u24lambda_u2d1.update(data, segmentPos$iv, byteCount);
            pos$iv = nextSegmentOffset$iv;
        }
        byte[] digestBytes = $this$digest_u24lambda_u2d1.digest();
        Intrinsics.checkNotNullExpressionValue(digestBytes, "digestBytes");
        return new ByteString(digestBytes);
    }

    @Override // okio.ByteString
    public ByteString hmac$okio(String algorithm, ByteString key) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            int segmentCount$iv = getSegments().length;
            int pos$iv = 0;
            for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
                int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
                int nextSegmentOffset$iv = getDirectory()[s$iv];
                byte[] data = getSegments()[s$iv];
                int byteCount = nextSegmentOffset$iv - pos$iv;
                mac.update(data, segmentPos$iv, byteCount);
                pos$iv = nextSegmentOffset$iv;
            }
            byte[] bArrDoFinal = mac.doFinal();
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "mac.doFinal()");
            return new ByteString(bArrDoFinal);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // okio.ByteString
    public String base64Url() {
        return toByteString().base64Url();
    }

    @Override // okio.ByteString
    public ByteString substring(int beginIndex, int endIndex) {
        int endIndex$iv = _UtilKt.resolveDefaultParameter(this, endIndex);
        int i = 1;
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex=" + beginIndex + " < 0").toString());
        }
        if (!(endIndex$iv <= size())) {
            throw new IllegalArgumentException(("endIndex=" + endIndex$iv + " > length(" + size() + ')').toString());
        }
        int subLen$iv = endIndex$iv - beginIndex;
        if (!(subLen$iv >= 0)) {
            throw new IllegalArgumentException(("endIndex=" + endIndex$iv + " < beginIndex=" + beginIndex).toString());
        }
        if (beginIndex == 0 && endIndex$iv == size()) {
            return this;
        }
        if (beginIndex == endIndex$iv) {
            return ByteString.EMPTY;
        }
        int beginSegment$iv = _SegmentedByteStringKt.segment(this, beginIndex);
        int endSegment$iv = _SegmentedByteStringKt.segment(this, endIndex$iv - 1);
        byte[][] newSegments$iv = (byte[][]) ArraysKt.copyOfRange(getSegments(), beginSegment$iv, endSegment$iv + 1);
        int[] newDirectory$iv = new int[newSegments$iv.length * 2];
        int index$iv = 0;
        if (beginSegment$iv <= endSegment$iv) {
            int i2 = beginSegment$iv;
            while (true) {
                int s$iv = i2;
                i2 += i;
                newDirectory$iv[index$iv] = Math.min(getDirectory()[s$iv] - beginIndex, subLen$iv);
                newDirectory$iv[index$iv + newSegments$iv.length] = getDirectory()[getSegments().length + s$iv];
                index$iv++;
                if (s$iv == endSegment$iv) {
                    break;
                }
                i = 1;
            }
        }
        int segmentOffset$iv = beginSegment$iv == 0 ? 0 : getDirectory()[beginSegment$iv - 1];
        int length = newSegments$iv.length;
        newDirectory$iv[length] = newDirectory$iv[length] + (beginIndex - segmentOffset$iv);
        return new SegmentedByteString(newSegments$iv, newDirectory$iv);
    }

    @Override // okio.ByteString
    public byte internalGet$okio(int pos) {
        _UtilKt.checkOffsetAndCount(getDirectory()[getSegments().length - 1], pos, 1L);
        int segment$iv = _SegmentedByteStringKt.segment(this, pos);
        int segmentOffset$iv = segment$iv == 0 ? 0 : getDirectory()[segment$iv - 1];
        int segmentPos$iv = getDirectory()[getSegments().length + segment$iv];
        return getSegments()[segment$iv][(pos - segmentOffset$iv) + segmentPos$iv];
    }

    @Override // okio.ByteString
    public int getSize$okio() {
        return getDirectory()[getSegments().length - 1];
    }

    @Override // okio.ByteString
    public byte[] toByteArray() {
        byte[] result$iv = new byte[size()];
        int resultPos$iv = 0;
        int segmentCount$iv$iv = getSegments().length;
        int pos$iv$iv = 0;
        for (int s$iv$iv = 0; s$iv$iv < segmentCount$iv$iv; s$iv$iv++) {
            int segmentPos$iv$iv = getDirectory()[segmentCount$iv$iv + s$iv$iv];
            int nextSegmentOffset$iv$iv = getDirectory()[s$iv$iv];
            byte[] data$iv = getSegments()[s$iv$iv];
            int byteCount$iv = nextSegmentOffset$iv$iv - pos$iv$iv;
            ArraysKt.copyInto(data$iv, result$iv, resultPos$iv, segmentPos$iv$iv, segmentPos$iv$iv + byteCount$iv);
            resultPos$iv += byteCount$iv;
            pos$iv$iv = nextSegmentOffset$iv$iv;
        }
        return result$iv;
    }

    @Override // okio.ByteString
    public ByteBuffer asByteBuffer() {
        ByteBuffer byteBufferAsReadOnlyBuffer = ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
        Intrinsics.checkNotNullExpressionValue(byteBufferAsReadOnlyBuffer, "wrap(toByteArray()).asReadOnlyBuffer()");
        return byteBufferAsReadOnlyBuffer;
    }

    @Override // okio.ByteString
    public void write(OutputStream out) throws IOException {
        Intrinsics.checkNotNullParameter(out, "out");
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            out.write(data, segmentPos$iv, byteCount);
            pos$iv = nextSegmentOffset$iv;
        }
    }

    @Override // okio.ByteString
    public void write$okio(Buffer buffer, int offset, int byteCount) {
        SegmentedByteString $this$commonWrite$iv;
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        SegmentedByteString $this$commonWrite$iv2 = this;
        int endIndex$iv$iv = offset + byteCount;
        int s$iv$iv = _SegmentedByteStringKt.segment($this$commonWrite$iv2, offset);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : $this$commonWrite$iv2.getDirectory()[s$iv$iv - 1];
            int segmentSize$iv$iv = $this$commonWrite$iv2.getDirectory()[s$iv$iv] - segmentOffset$iv$iv;
            int segmentPos$iv$iv = $this$commonWrite$iv2.getDirectory()[$this$commonWrite$iv2.getSegments().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + segmentSize$iv$iv) - pos$iv$iv;
            int offset$iv$iv = (pos$iv$iv - segmentOffset$iv$iv) + segmentPos$iv$iv;
            byte[] data$iv = $this$commonWrite$iv2.getSegments()[s$iv$iv];
            Segment segment$iv = new Segment(data$iv, offset$iv$iv, offset$iv$iv + byteCount$iv$iv, true, false);
            if (buffer.head == null) {
                segment$iv.prev = segment$iv;
                $this$commonWrite$iv = $this$commonWrite$iv2;
                segment$iv.next = segment$iv.prev;
                buffer.head = segment$iv.next;
            } else {
                $this$commonWrite$iv = $this$commonWrite$iv2;
                Segment segment = buffer.head;
                Intrinsics.checkNotNull(segment);
                Segment segment2 = segment.prev;
                Intrinsics.checkNotNull(segment2);
                segment2.push(segment$iv);
            }
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
            $this$commonWrite$iv2 = $this$commonWrite$iv;
        }
        buffer.setSize$okio(buffer.size() + byteCount);
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int offset, ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(other, "other");
        SegmentedByteString $this$commonRangeEquals$iv = this;
        if (offset < 0 || offset > $this$commonRangeEquals$iv.size() - byteCount) {
            return false;
        }
        int otherOffset$iv = otherOffset;
        int endIndex$iv$iv = offset + byteCount;
        int s$iv$iv = _SegmentedByteStringKt.segment($this$commonRangeEquals$iv, offset);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : $this$commonRangeEquals$iv.getDirectory()[s$iv$iv - 1];
            int segmentSize$iv$iv = $this$commonRangeEquals$iv.getDirectory()[s$iv$iv] - segmentOffset$iv$iv;
            int segmentPos$iv$iv = $this$commonRangeEquals$iv.getDirectory()[$this$commonRangeEquals$iv.getSegments().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + segmentSize$iv$iv) - pos$iv$iv;
            int offset$iv$iv = (pos$iv$iv - segmentOffset$iv$iv) + segmentPos$iv$iv;
            byte[] data$iv = $this$commonRangeEquals$iv.getSegments()[s$iv$iv];
            SegmentedByteString $this$commonRangeEquals$iv2 = $this$commonRangeEquals$iv;
            if (!other.rangeEquals(otherOffset$iv, data$iv, offset$iv$iv, byteCount$iv$iv)) {
                return false;
            }
            otherOffset$iv += byteCount$iv$iv;
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
            $this$commonRangeEquals$iv = $this$commonRangeEquals$iv2;
        }
        return true;
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (offset < 0 || offset > size() - byteCount || otherOffset < 0 || otherOffset > other.length - byteCount) {
            return false;
        }
        int otherOffset$iv = otherOffset;
        int endIndex$iv$iv = offset + byteCount;
        int s$iv$iv = _SegmentedByteStringKt.segment(this, offset);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : getDirectory()[s$iv$iv - 1];
            int segmentSize$iv$iv = getDirectory()[s$iv$iv] - segmentOffset$iv$iv;
            int segmentPos$iv$iv = getDirectory()[getSegments().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + segmentSize$iv$iv) - pos$iv$iv;
            int offset$iv$iv = segmentPos$iv$iv + (pos$iv$iv - segmentOffset$iv$iv);
            byte[] data$iv = getSegments()[s$iv$iv];
            if (!_UtilKt.arrayRangeEquals(data$iv, offset$iv$iv, other, otherOffset$iv, byteCount$iv$iv)) {
                return false;
            }
            otherOffset$iv += byteCount$iv$iv;
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
        }
        return true;
    }

    @Override // okio.ByteString
    public void copyInto(int offset, byte[] target, int targetOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(target, "target");
        SegmentedByteString $this$commonCopyInto$iv = this;
        _UtilKt.checkOffsetAndCount($this$commonCopyInto$iv.size(), offset, byteCount);
        _UtilKt.checkOffsetAndCount(target.length, targetOffset, byteCount);
        int targetOffset$iv = targetOffset;
        int endIndex$iv$iv = offset + byteCount;
        int s$iv$iv = _SegmentedByteStringKt.segment($this$commonCopyInto$iv, offset);
        int pos$iv$iv = offset;
        while (pos$iv$iv < endIndex$iv$iv) {
            int segmentOffset$iv$iv = s$iv$iv == 0 ? 0 : $this$commonCopyInto$iv.getDirectory()[s$iv$iv - 1];
            int segmentSize$iv$iv = $this$commonCopyInto$iv.getDirectory()[s$iv$iv] - segmentOffset$iv$iv;
            int segmentPos$iv$iv = $this$commonCopyInto$iv.getDirectory()[$this$commonCopyInto$iv.getSegments().length + s$iv$iv];
            int byteCount$iv$iv = Math.min(endIndex$iv$iv, segmentOffset$iv$iv + segmentSize$iv$iv) - pos$iv$iv;
            int offset$iv$iv = segmentPos$iv$iv + (pos$iv$iv - segmentOffset$iv$iv);
            byte[] data$iv = $this$commonCopyInto$iv.getSegments()[s$iv$iv];
            ArraysKt.copyInto(data$iv, target, targetOffset$iv, offset$iv$iv, offset$iv$iv + byteCount$iv$iv);
            targetOffset$iv += byteCount$iv$iv;
            pos$iv$iv += byteCount$iv$iv;
            s$iv$iv++;
            $this$commonCopyInto$iv = $this$commonCopyInto$iv;
        }
    }

    @Override // okio.ByteString
    public int indexOf(byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        return toByteString().indexOf(other, fromIndex);
    }

    @Override // okio.ByteString
    public int lastIndexOf(byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(other, "other");
        return toByteString().lastIndexOf(other, fromIndex);
    }

    private final ByteString toByteString() {
        return new ByteString(toByteArray());
    }

    @Override // okio.ByteString
    public byte[] internalArray$okio() {
        return toByteArray();
    }

    @Override // okio.ByteString
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof ByteString) {
            return ((ByteString) other).size() == size() && rangeEquals(0, (ByteString) other, 0, size());
        }
        return false;
    }

    @Override // okio.ByteString
    public int hashCode() {
        int result$iv = getHashCode();
        if (result$iv == 0) {
            result$iv = 1;
            int segmentCount$iv$iv = getSegments().length;
            int pos$iv$iv = 0;
            for (int s$iv$iv = 0; s$iv$iv < segmentCount$iv$iv; s$iv$iv++) {
                int segmentPos$iv$iv = getDirectory()[segmentCount$iv$iv + s$iv$iv];
                int nextSegmentOffset$iv$iv = getDirectory()[s$iv$iv];
                byte[] data$iv = getSegments()[s$iv$iv];
                int byteCount$iv = nextSegmentOffset$iv$iv - pos$iv$iv;
                int limit$iv = segmentPos$iv$iv + byteCount$iv;
                for (int i$iv = segmentPos$iv$iv; i$iv < limit$iv; i$iv++) {
                    result$iv = (result$iv * 31) + data$iv[i$iv];
                }
                pos$iv$iv = nextSegmentOffset$iv$iv;
            }
            setHashCode$okio(result$iv);
        }
        return result$iv;
    }

    @Override // okio.ByteString
    public String toString() {
        return toByteString().toString();
    }

    private final Object writeReplace() {
        return toByteString();
    }
}
