package com.naver.maps.map.overlay;

/* loaded from: classes.dex */
public enum Align {
    Center,
    Left,
    Right,
    Top,
    Bottom,
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight;

    public static final Align[] EDGES = {Bottom, Right, Left, Top};
    public static final Align[] APEXES = {BottomRight, BottomLeft, TopRight, TopLeft};
    public static final Align[] OUTSIDES = {Bottom, Right, Left, Top, BottomRight, BottomLeft, TopRight, TopLeft};
}
