package com.chris.hunger;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchStoreByAddressActivity extends Activity {

	private ImageView ivDeleteText;
	private EditText etSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_store_name_activity);
        ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
        etSearch = (EditText) findViewById(R.id.etSearch);
        
        ivDeleteText.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				etSearch.setText("");
			}
		});
        
        etSearch.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}
		});
    }

}
