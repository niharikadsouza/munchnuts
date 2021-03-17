package com.example.munchnuts.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.munchnuts.Offersavailable;
import com.example.munchnuts.R;

import java.util.ArrayList;

    public class OfflerListAdapter extends ArrayAdapter<Offersavailable> implements View.OnClickListener {

        public ArrayList<Offersavailable> dataSet;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
            TextView txtName;
            TextView txtType;

        }

        public OfflerListAdapter(ArrayList<Offersavailable> data, Context context) {
            super(context, R.layout.offerlist, data);
            this.dataSet = data;
            this.mContext=context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);
            Offersavailable dataModel=(Offersavailable) object;


        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Offersavailable dataModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.offerlist, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);


                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.txtName.setText(dataModel.getName());
            viewHolder.txtType.setText(dataModel.getType());


            // Return the completed view to render on screen
            return convertView;
        }
    }



