package com.google.android.gms.internal.maps;

import java.util.Arrays;
import java.util.Objects;
import javax.annotation.CheckForNull;
import kotlin.UByte;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzbt extends zzbl {
    static final zzbl zza = new zzbt(null, new Object[0], 0);
    final transient Object[] zzb;

    @CheckForNull
    private final transient Object zzc;
    private final transient int zzd;

    private zzbt(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzc = obj;
        this.zzb = objArr;
        this.zzd = i;
    }

    static zzbt zzg(int i, Object[] objArr, zzbk zzbkVar) {
        int i2 = i;
        Object[] objArrCopyOf = objArr;
        if (i2 == 0) {
            return (zzbt) zza;
        }
        Object[] objArr2 = null;
        if (i2 == 1) {
            zzbd.zza(Objects.requireNonNull(objArrCopyOf[0]), Objects.requireNonNull(objArrCopyOf[1]));
            return new zzbt(null, objArrCopyOf, 1);
        }
        zzba.zzb(i2, objArrCopyOf.length >> 1, "index");
        int iZzf = zzbm.zzf(i);
        if (i2 == 1) {
            zzbd.zza(Objects.requireNonNull(objArrCopyOf[0]), Objects.requireNonNull(objArrCopyOf[1]));
            i2 = 1;
        } else {
            int i3 = iZzf - 1;
            if (iZzf <= 128) {
                byte[] bArr = new byte[iZzf];
                Arrays.fill(bArr, (byte) -1);
                int i4 = 0;
                for (int i5 = 0; i5 < i2; i5++) {
                    int i6 = i4 + i4;
                    int i7 = i5 + i5;
                    Object objRequireNonNull = Objects.requireNonNull(objArrCopyOf[i7]);
                    Object objRequireNonNull2 = Objects.requireNonNull(objArrCopyOf[i7 ^ 1]);
                    zzbd.zza(objRequireNonNull, objRequireNonNull2);
                    int iZza = zzbe.zza(objRequireNonNull.hashCode());
                    while (true) {
                        int i8 = iZza & i3;
                        int i9 = bArr[i8] & UByte.MAX_VALUE;
                        if (i9 == 255) {
                            bArr[i8] = (byte) i6;
                            if (i4 < i5) {
                                objArrCopyOf[i6] = objRequireNonNull;
                                objArrCopyOf[i6 ^ 1] = objRequireNonNull2;
                            }
                            i4++;
                        } else {
                            if (objRequireNonNull.equals(objArrCopyOf[i9])) {
                                int i10 = i9 ^ 1;
                                zzbj zzbjVar = new zzbj(objRequireNonNull, objRequireNonNull2, Objects.requireNonNull(objArrCopyOf[i10]));
                                objArrCopyOf[i10] = objRequireNonNull2;
                                objArr2 = zzbjVar;
                                break;
                            }
                            iZza = i8 + 1;
                        }
                    }
                }
                objArr2 = i4 == i2 ? bArr : new Object[]{bArr, Integer.valueOf(i4), objArr2};
            } else if (iZzf <= 32768) {
                short[] sArr = new short[iZzf];
                Arrays.fill(sArr, (short) -1);
                int i11 = 0;
                for (int i12 = 0; i12 < i2; i12++) {
                    int i13 = i11 + i11;
                    int i14 = i12 + i12;
                    Object objRequireNonNull3 = Objects.requireNonNull(objArrCopyOf[i14]);
                    Object objRequireNonNull4 = Objects.requireNonNull(objArrCopyOf[i14 ^ 1]);
                    zzbd.zza(objRequireNonNull3, objRequireNonNull4);
                    int iZza2 = zzbe.zza(objRequireNonNull3.hashCode());
                    while (true) {
                        int i15 = iZza2 & i3;
                        char c = (char) sArr[i15];
                        if (c == 65535) {
                            sArr[i15] = (short) i13;
                            if (i11 < i12) {
                                objArrCopyOf[i13] = objRequireNonNull3;
                                objArrCopyOf[i13 ^ 1] = objRequireNonNull4;
                            }
                            i11++;
                        } else {
                            if (objRequireNonNull3.equals(objArrCopyOf[c])) {
                                int i16 = c ^ 1;
                                zzbj zzbjVar2 = new zzbj(objRequireNonNull3, objRequireNonNull4, Objects.requireNonNull(objArrCopyOf[i16]));
                                objArrCopyOf[i16] = objRequireNonNull4;
                                objArr2 = zzbjVar2;
                                break;
                            }
                            iZza2 = i15 + 1;
                        }
                    }
                }
                objArr2 = i11 == i2 ? sArr : new Object[]{sArr, Integer.valueOf(i11), objArr2};
            } else {
                int[] iArr = new int[iZzf];
                Arrays.fill(iArr, -1);
                int i17 = 0;
                for (int i18 = 0; i18 < i2; i18++) {
                    int i19 = i17 + i17;
                    int i20 = i18 + i18;
                    Object objRequireNonNull5 = Objects.requireNonNull(objArrCopyOf[i20]);
                    Object objRequireNonNull6 = Objects.requireNonNull(objArrCopyOf[i20 ^ 1]);
                    zzbd.zza(objRequireNonNull5, objRequireNonNull6);
                    int iZza3 = zzbe.zza(objRequireNonNull5.hashCode());
                    while (true) {
                        int i21 = iZza3 & i3;
                        int i22 = iArr[i21];
                        if (i22 == -1) {
                            iArr[i21] = i19;
                            if (i17 < i18) {
                                objArrCopyOf[i19] = objRequireNonNull5;
                                objArrCopyOf[i19 ^ 1] = objRequireNonNull6;
                            }
                            i17++;
                        } else {
                            if (objRequireNonNull5.equals(objArrCopyOf[i22])) {
                                int i23 = i22 ^ 1;
                                zzbj zzbjVar3 = new zzbj(objRequireNonNull5, objRequireNonNull6, Objects.requireNonNull(objArrCopyOf[i23]));
                                objArrCopyOf[i23] = objRequireNonNull6;
                                objArr2 = zzbjVar3;
                                break;
                            }
                            iZza3 = i21 + 1;
                        }
                    }
                }
                objArr2 = i17 == i2 ? iArr : new Object[]{iArr, Integer.valueOf(i17), objArr2};
            }
        }
        if (objArr2 instanceof Object[]) {
            Object[] objArr3 = objArr2;
            zzbkVar.zzc = (zzbj) objArr3[2];
            Object obj = objArr3[0];
            int iIntValue = ((Integer) objArr3[1]).intValue();
            objArrCopyOf = Arrays.copyOf(objArrCopyOf, iIntValue + iIntValue);
            objArr2 = obj;
            i2 = iIntValue;
        }
        return new zzbt(objArr2, objArrCopyOf, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
    @Override // com.google.android.gms.internal.maps.zzbl, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r10) {
        /*
            r9 = this;
            r0 = 0
            if (r10 != 0) goto L6
            r10 = r0
            goto La2
        L6:
            int r1 = r9.zzd
            java.lang.Object[] r2 = r9.zzb
            r3 = 1
            if (r1 != r3) goto L22
            r1 = 0
            r1 = r2[r1]
            java.lang.Object r1 = java.util.Objects.requireNonNull(r1)
            boolean r10 = r1.equals(r10)
            if (r10 == 0) goto L26
            r10 = r2[r3]
            java.lang.Object r10 = java.util.Objects.requireNonNull(r10)
            goto La2
        L22:
            java.lang.Object r1 = r9.zzc
            if (r1 != 0) goto L29
        L26:
            r10 = r0
            goto La2
        L29:
            boolean r4 = r1 instanceof byte[]
            r5 = -1
            if (r4 == 0) goto L56
            r4 = r1
            byte[] r4 = (byte[]) r4
            int r1 = r4.length
            int r6 = r1 + (-1)
            int r1 = r10.hashCode()
            int r1 = com.google.android.gms.internal.maps.zzbe.zza(r1)
        L3c:
            r1 = r1 & r6
            r5 = r4[r1]
            r7 = 255(0xff, float:3.57E-43)
            r5 = r5 & r7
            if (r5 != r7) goto L46
            r10 = r0
            goto La2
        L46:
            r7 = r2[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L53
            r10 = r5 ^ 1
            r10 = r2[r10]
            goto La2
        L53:
            int r1 = r1 + 1
            goto L3c
        L56:
            boolean r4 = r1 instanceof short[]
            if (r4 == 0) goto L83
            r4 = r1
            short[] r4 = (short[]) r4
            int r1 = r4.length
            int r6 = r1 + (-1)
            int r1 = r10.hashCode()
            int r1 = com.google.android.gms.internal.maps.zzbe.zza(r1)
        L68:
            r1 = r1 & r6
            short r5 = r4[r1]
            char r5 = (char) r5
            r7 = 65535(0xffff, float:9.1834E-41)
            if (r5 != r7) goto L73
            r10 = r0
            goto La2
        L73:
            r7 = r2[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L80
            r10 = r5 ^ 1
            r10 = r2[r10]
            goto La2
        L80:
            int r1 = r1 + 1
            goto L68
        L83:
            int[] r1 = (int[]) r1
            int r4 = r1.length
            int r4 = r4 + r5
            int r6 = r10.hashCode()
            int r6 = com.google.android.gms.internal.maps.zzbe.zza(r6)
        L8f:
            r6 = r6 & r4
            r7 = r1[r6]
            if (r7 != r5) goto L96
            r10 = r0
            goto La2
        L96:
            r8 = r2[r7]
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto La6
            r10 = r7 ^ 1
            r10 = r2[r10]
        La2:
            if (r10 != 0) goto La5
            return r0
        La5:
            return r10
        La6:
            int r6 = r6 + 1
            goto L8f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.maps.zzbt.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.maps.zzbl
    final zzbf zza() {
        return new zzbs(this.zzb, 1, this.zzd);
    }

    @Override // com.google.android.gms.internal.maps.zzbl
    final zzbm zzd() {
        return new zzbq(this, this.zzb, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.maps.zzbl
    final zzbm zze() {
        return new zzbr(this, new zzbs(this.zzb, 0, this.zzd));
    }
}
