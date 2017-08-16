package com.sm.cmdss;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rz Rasel on 2017-08-16.
 */

public class ARHAutoCompleteTextView extends AutoCompleteTextView {
    private static final int DEFAULT_HIGHLIGHT = Color.parseColor("#FF4081");
    private static final int DEFAULT_TEXTCOLOR = Color.parseColor("#80000000");
    private static final int DEFAULT_TEXT_PIXEL_SIZE = 14;
    private float mTextSize;
    private boolean mIsIgnoreCase;
    private ARHAdapter mAdapter;

    private ColorStateList mHighLightColor, mTextColor;

    private List<PopupTextBean> mSourceDatas, mTempDatas;
    private OnPopupItemClickListener mListener;

    public ARHAutoCompleteTextView(Context argContext) {
        this(argContext, null);
    }

    public ARHAutoCompleteTextView(Context argContext, AttributeSet argAttrs) {
        this(argContext, argAttrs, android.R.attr.autoCompleteTextViewStyle);
    }

    public ARHAutoCompleteTextView(Context argContext, AttributeSet argAttrs, int argDefStyleAttr) {
        super(argContext, argAttrs, argDefStyleAttr);
        init(argContext, argAttrs);
    }

    private void init(Context argContext, AttributeSet argAttrs) {
        if (argAttrs != null) {
            final TypedArray a = argContext.obtainStyledAttributes(argAttrs, R.styleable.ARHAutoCompleteTextView);
            mTextColor = a.getColorStateList(R.styleable.ARHAutoCompleteTextView_completionTextColor);
            mHighLightColor = a.getColorStateList(R.styleable.ARHAutoCompleteTextView_completionHighlightColor);
            mTextSize = a.getDimensionPixelSize(R.styleable.ARHAutoCompleteTextView_completionTextSize, DEFAULT_TEXT_PIXEL_SIZE);
            mIsIgnoreCase = a.getBoolean(R.styleable.ARHAutoCompleteTextView_completionIgnoreCase, false);
            a.recycle();
        }
        initListener();
    }

    private void initListener() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence argCharSequence, int argStart, int argCount, int argAfter) {
            }

            @Override
            public void onTextChanged(CharSequence argCharSequence, int argStart, int argBefore, int argCount) {
            }

            @Override
            public void afterTextChanged(Editable argCharSequence) {
                onInputTextChanged(argCharSequence.toString());
            }
        });
    }

    private void onInputTextChanged(String argInput) {
        matchResult(argInput);

        if (mAdapter.mList.size() == 0) {
            ARHAutoCompleteTextView.this.dismissDropDown();
            return;
        }
        mAdapter.notifyDataSetChanged();

        if (!ARHAutoCompleteTextView.this.isPopupShowing() || mAdapter.mList.size() > 0) {
            showDropDown();
        }

    }

    public void setDatas(final List<String> argStrings) {
        mAdapter = new ARHAdapter(getContext(), getResultDatas(argStrings));
        setAdapter(mAdapter);
    }

    public void setOnPopupItemClickListener(OnPopupItemClickListener argListener) {
        mListener = argListener;
        this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argParent, View argView, int argPosition, long argId) {
                if (mListener == null) {
                    return;
                }
                mListener.onPopupItemClick(ARHAutoCompleteTextView.this.getText().toString());
            }
        });

    }

    private void matchResult(String argInput) {
        List<PopupTextBean> datas = mSourceDatas;
        if (TextUtils.isEmpty(argInput) || datas == null || datas.size() == 0) {
            return;
        }

        List<PopupTextBean> newDatas = new ArrayList<PopupTextBean>();
        List<String> newDataStrings = new ArrayList<String>();
        for (PopupTextBean resultBean : datas) {
            int matchIndex = matchString(resultBean.mTarget, argInput, mIsIgnoreCase);
            if (-1 != matchIndex) {
                PopupTextBean bean = new PopupTextBean(resultBean.mTarget, matchIndex, matchIndex + argInput.length());
                newDatas.add(bean);
                newDataStrings.add(resultBean.mTarget);
            }
        }

        mTempDatas = new ArrayList<PopupTextBean>();
        mTempDatas.clear();
        mTempDatas.addAll(newDatas);

        mAdapter.mList.clear();
        mAdapter.mList.addAll(newDataStrings);
    }


    private List<String> getResultDatas(List<String> argStrings) {
        if (argStrings == null || argStrings.size() == 0) {
            return null;
        }

        List<PopupTextBean> list = new ArrayList<PopupTextBean>();
        for (String target : argStrings) {
            list.add(new PopupTextBean(target));
        }

        mSourceDatas = new ArrayList<PopupTextBean>();
        mSourceDatas.addAll(list);
        return argStrings;
    }

    public void setMatchIgnoreCase(boolean argIgnoreCase) {
        mIsIgnoreCase = argIgnoreCase;
    }

    public boolean getMatchIgnoreCase() {
        return mIsIgnoreCase;
    }

    class ARHAdapter extends BaseAdapter implements Filterable {
        private List<String> mList;
        private Context mContext;
        private MyFilter mFilter;

        public ARHAdapter(Context argContext, List<String> argList) {
            mContext = argContext;
            mList = new ArrayList<String>();
            mList.addAll(argList);
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int argPosition) {
            return mList == null ? null : mList.get(argPosition);
        }

        @Override
        public long getItemId(int argPosition) {
            return argPosition;
        }

        @Override
        public View getView(int argPosition, View argConvertView, ViewGroup argParent) {
            ViewHolder holder = null;
            if (argConvertView == null) {
                holder = new ViewHolder();
                TextView tv = new TextView(mContext);
                int paddingX = DisplayUtils.dp2px(getContext(), 10.0f);
                int paddingY = DisplayUtils.dp2px(getContext(), 5.0f);
                tv.setPadding(paddingX, paddingY, paddingX, paddingY);

                holder.tv = tv;
                argConvertView = tv;
                argConvertView.setTag(holder);
            } else {
                holder = (ViewHolder) argConvertView.getTag();
            }

            PopupTextBean bean = mTempDatas.get(argPosition);
            SpannableString ss = new SpannableString(bean.mTarget);
            holder.tv.setTextColor(mTextColor == null ? DEFAULT_TEXTCOLOR : mTextColor.getDefaultColor());
            holder.tv.setTextSize(mTextSize == 0 ? DEFAULT_TEXT_PIXEL_SIZE : DisplayUtils.px2sp(getContext(), mTextSize));

            // Change Highlight Color
            if (-1 != bean.mStartIndex) {
                ss.setSpan(new ForegroundColorSpan(mHighLightColor == null ? DEFAULT_HIGHLIGHT : mHighLightColor.getDefaultColor()),
                        bean.mStartIndex, bean.mEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv.setText(ss);
            } else {
                holder.tv.setText(bean.mTarget);
            }

            return argConvertView;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }

        private class ViewHolder {
            TextView tv;
        }

        private class MyFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence argConstraint) {
                FilterResults results = new FilterResults();
                if (mList == null) {
                    mList = new ArrayList<String>();
                }
                results.values = mList;
                results.count = mList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence argConstraint, FilterResults argResults) {
                if (argResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }

    public interface OnPopupItemClickListener {
        void onPopupItemClick(CharSequence argCharSequence);
    }

    private static int[] next(char[] argMode) {
        int[] next = new int[argMode.length];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < argMode.length - 1) {
            if (j == -1 || argMode[i] == argMode[j]) {
                i++;
                j++;
                if (argMode[i] != argMode[j]) {
                    next[i] = j;
                } else {
                    next[i] = next[j];
                }
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public int matchString(CharSequence argSource, CharSequence argModeStr, boolean argIsIgnoreCase) {
        char[] modeArr = argModeStr.toString().toCharArray();
        char[] sourceArr = argSource.toString().toCharArray();
        int[] next = next(modeArr);
        int i = 0;
        int j = 0;
        while (i <= sourceArr.length - 1 && j <= modeArr.length - 1) {
            if (argIsIgnoreCase) {
                if (j == -1 || sourceArr[i] == modeArr[j] || String.valueOf(sourceArr[i]).equalsIgnoreCase(String.valueOf(modeArr[j]))) {
                    i++;
                    j++;
                } else {
                    j = next[j];
                }
            } else {
                if (j == -1 || sourceArr[i] == modeArr[j]) {
                    i++;
                    j++;
                } else {
                    j = next[j];
                }
            }
        }
        if (j < modeArr.length) {
            return -1;
        } else
            return i - modeArr.length;
    }

    public class PopupTextBean implements Serializable {
        public String mTarget;
        public int mStartIndex = -1;
        public int mEndIndex = -1;

        public PopupTextBean(String argTarget) {
            this.mTarget = argTarget;
        }

        public PopupTextBean(String argTarget, int argStartIndex) {
            this.mTarget = argTarget;
            this.mStartIndex = argStartIndex;
            if (-1 != argStartIndex) {
                this.mEndIndex = argStartIndex + argTarget.length();
            }
        }

        public PopupTextBean(String argTarget, int argStartIndex, int argEndIndex) {
            this.mTarget = argTarget;
            this.mStartIndex = argStartIndex;
            this.mEndIndex = argEndIndex;
        }
    }

    public static class DisplayUtils {
        public static int dp2px(Context argContext, float argDpValue) {
            final float scale = argContext.getResources().getDisplayMetrics().density;
            return (int) (argDpValue * scale + 0.5f);
        }

        public static int px2sp(Context argContext, float argPxValue) {
            final float fontScale = argContext.getResources().getDisplayMetrics().scaledDensity;
            return (int) (argPxValue / fontScale + 0.5f);
        }
    }
}
/*
Usages:
Attrs:
<declare-styleable name="ARHAutoCompleteTextView">
        <attr name="completionHighlightColor" format="reference|color" />
        <attr name="completionTextColor" format="reference|color" />
        <attr name="completionTextSize" format="dimension" />
        <attr name="completionIgnoreCase" format="boolean" />
    </declare-styleable>
Xml Layout:
<com.sm.cmdss.ARHAutoCompleteTextView
        android:id="@+id/tvAutoCompl"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:layout_marginTop="30dp"
        android:completionThreshold="1"
        app:completionHighlightColor="@color/colorAccent"
        app:completionIgnoreCase="true"
        app:completionTextColor="#80000000"
        app:completionTextSize="18sp" />
    <!--xmlns:app="http://schemas.android.com/apk/res-auto"-->
Java Code:
List<String> data = new ArrayList<String>();
        data.add("Red roses for wedding");
        data.add("Bouquet with red roses");
        data.add("Single red rose flower");

        ARHAutoCompleteTextView complTextView = (ARHAutoCompleteTextView) findViewById(R.id.tvAutoCompl);
        complTextView.setDatas(data);
        complTextView.setOnPopupItemClickListener(new ARHAutoCompleteTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(CharSequence argCharSequence) {
                Toast.makeText(context, argCharSequence.toString(), Toast.LENGTH_SHORT).show();
            }
        });


ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, books);

mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.act);
mAutoCompleteTextView.setAdapter(adapter);
https://github.com/AntarjotSingh/AutoCompleteTextView/blob/master/app/src/main/java/com/example/i322051/autocompletetextview/AutoCompleteAdapter.java
https://www.javatpoint.com/android-autocompletetextview-example
www.journaldev.com/9574/android-autocompletetextview-example-tutorial
https://stackoverflow.com/questions/21551318/how-to-extend-edittext-autocompletetextview-to-show-the-last-n-inputs
Auto Complete Bubble Text View
https://github.com/FrederickRider/AutoCompleteBubbleText
*/