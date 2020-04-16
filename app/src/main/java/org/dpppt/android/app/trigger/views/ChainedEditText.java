/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.trigger.views;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashSet;
import java.util.Set;

import org.dpppt.android.app.R;

public class ChainedEditText extends ConstraintLayout {

	private static final int NUM_CHARACTERS = 6;
	private static final String ID_TEXT_FIELD = "chained_edit_text_view_";

	private EditText shadowEditText;
	private View textViewGroup;
	private TextView[] textViews = new TextView[6];

	private Set<ChainedEditTextListener> chainedEditTextListeners = new HashSet<>();

	public ChainedEditText(Context context) {
		super(context);
		init(context, null, 0);
	}

	public ChainedEditText(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public ChainedEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		textViewGroup = LayoutInflater.from(context).inflate(R.layout.view_chained_edit_text, this, true);
		for (int i = 0; i < NUM_CHARACTERS; i++) {
			textViews[i] = textViewGroup
					.findViewById(getResources().getIdentifier(ID_TEXT_FIELD + (i + 1), "id", context.getPackageName()));
		}

		shadowEditText = new EditText(context);
		shadowEditText.setHeight(1);
		shadowEditText.setWidth(1);
		shadowEditText.setBackgroundColor(Color.TRANSPARENT);
		shadowEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		shadowEditText.setImeOptions(EditorInfo.IME_ACTION_SEND);
		shadowEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > NUM_CHARACTERS) {
					s.delete(NUM_CHARACTERS, s.length());
				}
				updateTextViews();
				String input = s.toString();
				for (ChainedEditTextListener listener : chainedEditTextListeners) {
					listener.onTextChanged(input);
				}
			}
		});
		shadowEditText.setOnFocusChangeListener((v, hasFocus) -> {
			updateTextViews();
			setKeyboardVisible(hasFocus);
		});
		shadowEditText.setOnEditorActionListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_SEND && chainedEditTextListeners.size() > 0) {
				for (ChainedEditTextListener listener : chainedEditTextListeners) {
					listener.onEditorSendAction();
				}
				return true;
			}
			return false;
		});
		addView(shadowEditText);

		textViewGroup.setOnClickListener(v -> {
			focusEditText();
			setKeyboardVisible(true);
		});
		textViewGroup.setOnFocusChangeListener((v, hasFocus) -> {
			if (hasFocus) {
				focusEditText();
			}
		});
	}

	private void focusEditText() {
		shadowEditText.requestFocus();
		shadowEditText.setSelection(shadowEditText.getText().length());
	}

	private void updateTextViews() {
		String input = shadowEditText.getText().toString();
		boolean hasFocus = shadowEditText.hasFocus();
		for (int i = 0; i < NUM_CHARACTERS; i++) {
			TextView textView = textViews[i];
			if (i < input.length()) {
				textView.setText(String.valueOf(input.charAt(i)));
			} else {
				textView.setText("");
			}
			textView.setSelected(hasFocus && i == Math.max(0, input.length() - 1));
		}
	}

	private void setKeyboardVisible(boolean visible) {
		InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (visible) {
			inputMethodManager.showSoftInput(shadowEditText, InputMethodManager.SHOW_IMPLICIT);
		} else {
			inputMethodManager.hideSoftInputFromWindow(shadowEditText.getWindowToken(), 0);
		}
	}

	public String getText() {
		return shadowEditText.getText().toString();
	}

	public void addTextChangedListener(ChainedEditTextListener chainedEditTextListener) {
		chainedEditTextListeners.add(chainedEditTextListener);
	}

	public void removeTextChangedListener(ChainedEditTextListener chainedEditTextListener) {
		chainedEditTextListeners.remove(chainedEditTextListener);
	}

	public interface ChainedEditTextListener {
		void onTextChanged(String input);

		void onEditorSendAction();

	}

}
