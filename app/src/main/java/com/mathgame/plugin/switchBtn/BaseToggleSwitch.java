package com.mathgame.plugin.switchBtn;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mathgame.R;

import java.util.ArrayList;


/**
 * Created by lorenzorigato on 4/1/16.
 */
public abstract class BaseToggleSwitch extends LinearLayout implements View.OnClickListener {

    private OnToggleSwitchChangeListener mOnToggleSwitchChangeListener = null;
    private int   activeBgColor;
    private int   activeTextColor;
    private int   inactiveBgColor;
    private int   inactiveTextColor;
    private int   separatorColor;
    private int   textSize;
    private float cornerRadius;
    private float toggleWidth;
    private LinearLayout      toggleSwitchesContainer;
    private ArrayList<String> mLabels;
    private Context           mContext;
    public BaseToggleSwitch(Context context) {
        this(context, null);
    }
    public BaseToggleSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ToggleSwitchOptions, 0, 0);

            try {
                mContext = context;

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mInflater.inflate(R.layout.widget_toggle_switch, this, true);

                toggleSwitchesContainer = findViewById(R.id.toggle_switches_container);

                String centerToggleText = attributes.getString(R.styleable.ToggleSwitchOptions_textToggleCenter);
                String leftToggleText = attributes.getString(R.styleable.ToggleSwitchOptions_textToggleLeft);
                String rightToggleText = attributes.getString(R.styleable.ToggleSwitchOptions_textToggleRight);

                this.activeBgColor = attributes.getColor(R.styleable.ToggleSwitchOptions_activeBgColor, ContextCompat.getColor(context, Default.ACTIVE_BG_COLOR));
                this.activeTextColor = attributes.getColor(R.styleable.ToggleSwitchOptions_activeTextColor, ContextCompat.getColor(context, Default.ACTIVE_TEXT_COLOR));
                this.inactiveBgColor = attributes.getColor(R.styleable.ToggleSwitchOptions_inactiveBgColor, ContextCompat.getColor(context, Default.INACTIVE_BG_COLOR));
                this.inactiveTextColor = attributes.getColor(R.styleable.ToggleSwitchOptions_inactiveTextColor, ContextCompat.getColor(context, Default.INACTIVE_TEXT_COLOR));
                this.separatorColor = attributes.getColor(R.styleable.ToggleSwitchOptions_separatorColor, ContextCompat.getColor(context, Default.SEPARATOR_COLOR));
                this.textSize = attributes.getDimensionPixelSize(R.styleable.ToggleSwitchOptions_android_textSize, (int) dp2px(context, Default.TEXT_SIZE));
                this.toggleWidth = attributes.getDimension(R.styleable.ToggleSwitchOptions_toggleWidth, dp2px(getContext(), Default.TOGGLE_WIDTH));
                this.cornerRadius = attributes.getDimensionPixelSize(R.styleable.ToggleSwitchOptions_cornerRadius, (int) dp2px(context, Default.CORNER_RADIUS_DP));

                if (leftToggleText != null && !leftToggleText.isEmpty() &&
                        rightToggleText != null && !rightToggleText.isEmpty()) {
                    mLabels = new ArrayList<>();
                    mLabels.add(leftToggleText);
                    if (centerToggleText != null && !centerToggleText.isEmpty())
                        mLabels.add(centerToggleText);
                    mLabels.add(rightToggleText);
                    buildToggleButtons();
                }
            } finally {
                attributes.recycle();
            }
        }
    }

    public int getActiveBgColor() {
        return activeBgColor;
    }

    public void setActiveBgColor(int activeBgColor) {
        this.activeBgColor = activeBgColor;
    }

    // *************** GETTERS AND SETTERS ****************

    public int getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(int activeTextColor) {
        this.activeTextColor = activeTextColor;
    }

    public int getInactiveBgColor() {
        return inactiveBgColor;
    }

    public void setInactiveBgColor(int inactiveBgColor) {
        this.inactiveBgColor = inactiveBgColor;
    }

    public int getInactiveTextColor() {
        return inactiveTextColor;
    }

    public void setInactiveTextColor(int inactiveTextColor) {
        this.inactiveTextColor = inactiveTextColor;
    }

    public int getSeparatorColor() {
        return separatorColor;
    }

    public void setSeparatorColor(int separatorColor) {
        this.separatorColor = separatorColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getToggleWidth() {
        return toggleWidth;
    }

    public void setToggleWidth(float toggleWidth) {
        this.toggleWidth = toggleWidth;
    }

    void activate(int position) {
        setColors(getToggleSwitchButton(position), activeBgColor, activeTextColor);
    }

    private void addToogleBtn(String text) {

        ToggleSwitchButton toggleSwitchButton = new ToggleSwitchButton(mContext);

        TextView toggleBtnTxt = toggleSwitchButton.getTextView();
        toggleBtnTxt.setText(text);
        toggleBtnTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        LayoutParams params = new LayoutParams((int) toggleWidth, LayoutParams.WRAP_CONTENT);
        if (toggleWidth == 0f) params.weight = 1f;
        toggleBtnTxt.setLayoutParams(params);

        toggleSwitchButton.getSeparator().setBackgroundColor(separatorColor);

        toggleSwitchButton.getTextView().setOnClickListener(this);

        LayoutParams layoutParams = new LayoutParams((int) toggleWidth, LayoutParams.MATCH_PARENT);
        if (toggleWidth == 0f) layoutParams.weight = 1f;
        toggleSwitchesContainer.addView(toggleSwitchButton.getView(), layoutParams);

        // Disable last added button
        disable(toggleSwitchesContainer.getChildCount() - 1);
    }

    // *********** PRIVATE INSTANCE METHODS ************

    private RoundRectShape buildRect(ToggleSwitchButton toggleSwitchButton) {
        if (isFirst(toggleSwitchButton))
            return new RoundRectShape(
                    new float[]{cornerRadius, cornerRadius, 0, 0, 0, 0, cornerRadius, cornerRadius},
                    null,
                    null);
        else if (isLast(toggleSwitchButton))
            return new RoundRectShape(
                    new float[]{0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0},
                    null,
                    null);
        else
            return new RoundRectShape(
                    new float[]{0, 0, 0, 0, 0, 0, 0, 0},
                    null,
                    null);
    }

    void buildToggleButtons() {
        for (String label : mLabels)
            addToogleBtn(label);
    }

    private void disable(int position) {
        setColors(getToggleSwitchButton(position), inactiveBgColor, inactiveTextColor);
    }

    void disableAll() {
        for (int i = 0; i < toggleSwitchesContainer.getChildCount(); i++)
            disable(i);
    }

    protected abstract boolean isActive(int position);

    protected int getNumButtons() {
        return getToggleSwitchesContainer().getChildCount();
    }

    LinearLayout getToggleSwitchesContainer() {
        return toggleSwitchesContainer;
    }

    protected int getToggleIndex(ToggleSwitchButton toggleSwitchButton) {
        return toggleSwitchesContainer.indexOfChild(toggleSwitchButton.getView());
    }

    private ToggleSwitchButton getToggleSwitchButton(int position) {
        return new ToggleSwitchButton(toggleSwitchesContainer.getChildAt(position));
    }

    protected boolean isLast(int position) {
        return position == getToggleSwitchesContainer().getChildCount() - 1;
    }

    @Override
    public void onClick(View v) {
        LinearLayout toggleSwitchButton = (LinearLayout) v.getParent();
        int position = toggleSwitchesContainer.indexOfChild(toggleSwitchButton);
        onClickOnToggleSwitch(position);
    }

    protected abstract void onClickOnToggleSwitch(int position);

    private void setColors(ToggleSwitchButton toggleSwitchButton, int bgColor, int textColor) {
        ShapeDrawable sd = new ShapeDrawable(buildRect(toggleSwitchButton));
        sd.getPaint().setColor(bgColor);
        toggleSwitchButton.getView().setBackground(sd);
        toggleSwitchButton.getTextView().setTextColor(textColor);
    }

    public void setLabels(ArrayList<String> labels) {
        if (labels == null || labels.isEmpty())
            throw new RuntimeException("The list of labels must contains at least 2 elements");
        mLabels = labels;
        toggleSwitchesContainer.removeAllViews();
        buildToggleButtons();
    }

    public void setOnToggleSwitchChangeListener(OnToggleSwitchChangeListener onToggleSwitchChangeListener) {
        this.mOnToggleSwitchChangeListener = onToggleSwitchChangeListener;
    }

    void notifyOnToggleChange(int position) {
        if (mOnToggleSwitchChangeListener != null)
            mOnToggleSwitchChangeListener.onToggleSwitchChangeListener(position, isActive(position));
    }

    public void setTogglePadding(int left, int top, int right, int bottom) {
        for (int i = 0; i < toggleSwitchesContainer.getChildCount(); i++) {
            ToggleSwitchButton toggleSwitchButton = new ToggleSwitchButton(toggleSwitchesContainer.getChildAt(i));
            toggleSwitchButton.getTextView().setPadding(left, top, right, bottom);
        }
    }

    private float dp2px(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    private boolean isFirst(ToggleSwitchButton toggleSwitchButton) {
        return toggleSwitchesContainer.indexOfChild(toggleSwitchButton.getView()) == 0;
    }

    // **************** UTILS *****************

    private boolean isLast(ToggleSwitchButton toggleSwitchButton) {
        int lastPosition = toggleSwitchesContainer.getChildCount() - 1;
        return toggleSwitchesContainer.indexOfChild(toggleSwitchButton.getView()) == lastPosition;
    }

    interface OnToggleSwitchChangeListener {
        void onToggleSwitchChangeListener(int position, boolean isChecked);
    }

    static class Default {

        static final int   ACTIVE_BG_COLOR     = R.color.blue;
        static final int   ACTIVE_TEXT_COLOR   = android.R.color.white;
        static final int   INACTIVE_BG_COLOR   = R.color.gray_light;
        static final int   INACTIVE_TEXT_COLOR = R.color.gray;
        static final int   SEPARATOR_COLOR     = R.color.gray_very_light;
        static final int   CORNER_RADIUS_DP    = 4;
        static final float TEXT_SIZE           = 12;
        static final float TOGGLE_WIDTH        = 64;
    }
}
