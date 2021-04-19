package com.spisoft.splateralbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nshmura.snappysmoothscroller.LinearLayoutScrollVectorDetector;
import com.nshmura.snappysmoothscroller.SnappySmoothScroller;
import com.spisoft.spcircleview.CircleView;

public class SpLateBox extends RelativeLayout {
    private View rootView;
    private Context mContext;
    private CircleView IvsHead;
    private RecyclerView IncRecyclerView;
    private LinearLayoutManager HorizontalLayout;
    private TextView vText;
    private CircleView vBefore, vNext, vEdit, vAdd;
    private Drawable mIconAdd, mIconEdit, mIconSave, mIconCancel;
    private RelativeLayout vMain;
    private OnAddClickTaskListener mOnAddClickTaskListener;
    private OnEditClickTaskListener mOnEditClickTaskListener;
    private int currentMode;
    private boolean isEditMode = false;
    private View LyCnt;

    public SpLateBox(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public SpLateBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public SpLateBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView(Context context, AttributeSet attrs, int defStyle) {
        rootView = inflate(context, R.layout.sps_view_box, this);

        mContext = context;

        IvsHead = rootView.findViewById(R.id.cv_head);
        IncRecyclerView = rootView.findViewById(R.id.incRecyclerView);

        LyCnt = rootView.findViewById(R.id.lyCnt);
        vBefore = rootView.findViewById(R.id.cv_before);
        vNext = rootView.findViewById(R.id.cv_next);

        vEdit = rootView.findViewById(R.id.cv_edit);
        vAdd = rootView.findViewById(R.id.cv_add);

        vMain = rootView.findViewById(R.id.lyMain);

        vText = rootView.findViewById(R.id.txtDesc);

        Configuration config = getResources().getConfiguration();
        if(config.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
        }else {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
        }

        vNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
                if(__CurrentPosition < HorizontalLayout.getItemCount())
                    HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null ,HorizontalLayout.findFirstVisibleItemPosition()+1);
//                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()+1, 0);
            }
        });

        vBefore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
                if(__CurrentPosition > 0)
                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()-1, 0);
//                if(__CurrentPosition == 1)
            }
        });

        vEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnEditClickTaskListener.onEvent(isEditMode, HorizontalLayout.findFirstVisibleItemPosition());
                if (!isEditMode)
                    setMode(true);
            }
        });

        vAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddClickTaskListener.onEvent(isEditMode);
                if (!isEditMode)
                    setMode(true);
            }
        });

        //-------------------------------------------
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpLateBox, defStyle, 0);

        int drawableResId_Add = a.getResourceId(R.styleable.SpLateBox_icon_add, -1);
        if(drawableResId_Add >= 0)
            mIconAdd = getResources().getDrawable(drawableResId_Add);
        else
            mIconAdd = getResources().getDrawable(R.drawable.ic_baseline_add_24);
        vAdd.setIcon(mIconAdd);

        int drawableResId_Edit = a.getResourceId(R.styleable.SpLateBox_icon_edit, -1);
        if(drawableResId_Edit >= 0)
            mIconEdit = getResources().getDrawable(drawableResId_Edit);
        else
            mIconEdit = getResources().getDrawable(R.drawable.ic_baseline_edit_24);
        vEdit.setIcon(mIconEdit);

//        int drawableResId_Add = a.getResourceId(R.styleable.SpLateBox_icon_add, -1);
//        if(drawableResId_Add >= 0) vAdd.setIcon(getResources().getDrawable(drawableResId_Add));

//        int drawableResId_Edit = a.getResourceId(R.styleable.SpLateBox_icon_edit, -1);
//        if(drawableResId_Edit >= 0) vEdit.setIcon(getResources().getDrawable(drawableResId_Edit));

        int drawableResId_Save = a.getResourceId(R.styleable.SpLateBox_icon_save, -1);
        if(drawableResId_Save >= 0)
            mIconSave = getResources().getDrawable(drawableResId_Save);
        else
            mIconSave = getResources().getDrawable(R.drawable.ic_baseline_check_24);

        int drawableResId_Cancel = a.getResourceId(R.styleable.SpLateBox_icon_cancel, -1);
        if(drawableResId_Cancel >= 0)
            mIconCancel = getResources().getDrawable(drawableResId_Cancel);
        else
            mIconCancel = getResources().getDrawable(R.drawable.ic_baseline_close_24);

        a.recycle();
    }

    public void setMode(boolean editMode){
        if (editMode)
        {
            vEdit.setVisibility(VISIBLE);
            vEdit.setIcon(mIconSave);
            vAdd.setIcon(mIconCancel);
            LyCnt.setVisibility(GONE);
            IncRecyclerView.setVisibility(GONE);
            vMain.setVisibility(VISIBLE);
        }
        else
        {
            if(IncRecyclerView.getAdapter() == null || IncRecyclerView.getAdapter().getItemCount() == 0){
                vEdit.setVisibility(GONE);
                LyCnt.setVisibility(GONE);
            }
            else
                LyCnt.setVisibility(VISIBLE);

            vEdit.setIcon(mIconEdit);
            vAdd.setIcon(mIconAdd);
            IncRecyclerView.setVisibility(VISIBLE);
            vMain.setVisibility(GONE);
        }

        isEditMode = editMode;
    }

    //        currentMode = mode;
//        switch (mode){
//            case 0:
//                vEdit.setVisibility(GONE);
//                vNext.setVisibility(GONE);
//                vBefore.setVisibility(GONE);
//                vAdd.setVisibility(VISIBLE);
//                vAdd.set
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//        }

    @SuppressLint("UseCompatLoadingForDrawables")
    public SpLateBox SetHeadSrc(int headSrc){
        IvsHead.setIcon(getResources().getDrawable(headSrc));
        invalidate();
        return this;
    }

    public SpLateBox SetList(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
//        HorizontalLayout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//        IncRecyclerView.setLayoutManager(HorizontalLayout);
//        IncRecyclerView.setAdapter(adapter);
//        IncRecyclerView.invalidate();

//        SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(mContext);
//        layoutManager.setSnapType(SnapType.CENTER);
//        layoutManager.setSnapInterpolator(new DecelerateInterpolator());
        IncRecyclerView.setAdapter(adapter);

        HorizontalLayout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                SnappySmoothScroller scroller = new SnappySmoothScroller.Builder()
                        .setPosition(position)
                        .setScrollVectorDetector(new LinearLayoutScrollVectorDetector(this))
                        .build(recyclerView.getContext());

                startSmoothScroll(scroller);
            }

            @Override
            public boolean canScrollHorizontally() {
                return super.canScrollHorizontally();
//                return false;
            }
        };

        IncRecyclerView.setLayoutManager(HorizontalLayout);
        HorizontalLayout.setSmoothScrollbarEnabled(false);

        IncRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }
        });

        IncRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                String CurrentItem = String.valueOf(HorizontalLayout.findFirstVisibleItemPosition()+1);
                vText.setText("  " + CurrentItem + "/" + HorizontalLayout.getItemCount() + "  ");
            }
        });

        if(adapter.getItemCount() > 0){
            LyCnt.setVisibility(VISIBLE);
            vEdit.setVisibility(VISIBLE);
        }

        IncRecyclerView.invalidate();
        return this;
    }

    public SpLateBox AddView(View view){
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View inflatedLayout= inflater.inflate()
        vMain.addView(view);
//        vMain.setVisibility(VISIBLE);
        invalidate();
        return this;
    }

    public interface OnAddClickTaskListener {
        void onEvent(boolean editMode);
    }

    public void setOnAddClickTaskListener(OnAddClickTaskListener eventListener) {
        mOnAddClickTaskListener = eventListener;
    }

    public interface OnEditClickTaskListener {
        void onEvent(boolean editMode, int currentPosition);
    }

    public void setOnEditClickTaskListener(OnEditClickTaskListener eventListener) {
        mOnEditClickTaskListener = eventListener;
    }
}