package com.linhao.androidmodule.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.linhao.androidmodule.R;


public class UpdateDialog {
    private Context context;
    private Dialog dialog;
    UpdateDialogClick dialogClick;
    public Button btn_sure;
    public TextView dialog_title;
    public TextView view_text;

    public UpdateDialog(Context context, String title, String content) {
        this.context = context;
        dialog = new Dialog(context, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		int width = RmApplication.getInstance().getWidthPixels();
//		int height = RmApplication.getInstance().getHeiPixels();
//		LayoutParams params = new RelativeLayout.LayoutParams(width, height / 3);
        //	View dialog_view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
        View dialog_view = View.inflate(context, R.layout.update_dialog, null);
//		dialog.setContentView(dialog_view, params);
        dialog.setContentView(dialog_view);

        dialog.setOnKeyListener(keylistener); // 返回键不会退出程序
        dialog.setCancelable(false); // true点击屏幕以外关闭dialog

        btn_sure = (Button) dialog_view.findViewById(R.id.btn_dialog_yes);
        Button btn_no = (Button) dialog_view.findViewById(R.id.btn_dialog_no);
        view_text = (TextView) dialog_view.findViewById(R.id.view_text);
        view_text.setText(content);
        dialog_title = (TextView) dialog_view.findViewById(R.id.dialog_title);
        dialog_title.setText(title);

        btn_sure.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (dialogClick != null)
                    dialogClick.sure();
            }
        });

        btn_no.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (dialogClick != null)
                    dialogClick.noSure();
                dissmiss();
            }
        });
    }

    public void show() {
        dialog.show();
    }

    public void dissmiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return false;
            } else {
                return true;
            }
        }
    };

    public void setOnDialogClickListener(UpdateDialogClick dc) {
        dialogClick = dc;
    }

    public interface UpdateDialogClick {
        void sure();

        void noSure();
    }
}
