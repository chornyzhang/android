package com.hoperun.chorny.customlistview.adapter.viewhodler;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Chorny on 2017/2/25.
 */
public class ViewHolder {
        //view container
        private final SparseArray<View> mViews;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            //setTag
            mConvertView.setTag(this);


        }

        /**
         * get the ViewHolder
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {

            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }


        /**
         * get component id or add to views
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }





}
