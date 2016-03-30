package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by debaerdm on 30/03/16.
 */
public class TextViewNbChar{

    private TextView textView;
    private EditText editText;

    public TextViewNbChar(EditText editText, TextView textView){
        this.editText = editText;
        this.textView = textView;
        textView.setTextColor(Color.GREEN);
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int nbChar = getEditText().getText().toString().length();
                int leftChar = 300 - nbChar;
                String restant = "";

                if(leftChar == 0 || leftChar == 1){
                    restant = " caractère restant.";
                }else{
                    restant = " caractères restants.";
                }

                getTextView().setText(Integer.toString(leftChar) + restant);
                getTextView().setTextColor(Color.GREEN);

                if (leftChar < 50 && leftChar >= 11) {
                    getTextView().setTextColor(Color.rgb(255, 165, 0));
                }else if (leftChar <= 10 && leftChar >= 0) {
                    getTextView().setTextColor(Color.RED);
                }

            }
        });
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }


}
