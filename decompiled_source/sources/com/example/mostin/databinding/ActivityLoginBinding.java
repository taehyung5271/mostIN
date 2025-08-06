package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ActivityLoginBinding implements ViewBinding {
    public final Button btnLogin;
    public final EditText editTextId;
    public final EditText editTextPassword;
    private final LinearLayout rootView;

    private ActivityLoginBinding(LinearLayout rootView, Button btnLogin, EditText editTextId, EditText editTextPassword) {
        this.rootView = rootView;
        this.btnLogin = btnLogin;
        this.editTextId = editTextId;
        this.editTextPassword = editTextPassword;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_login, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityLoginBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_login;
        Button btnLogin = (Button) ViewBindings.findChildViewById(rootView, id);
        if (btnLogin != null) {
            id = R.id.edit_text_id;
            EditText editTextId = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editTextId != null) {
                id = R.id.edit_text_password;
                EditText editTextPassword = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (editTextPassword != null) {
                    return new ActivityLoginBinding((LinearLayout) rootView, btnLogin, editTextId, editTextPassword);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
