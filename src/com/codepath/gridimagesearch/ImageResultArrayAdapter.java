package com.codepath.gridimagesearch;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResults> {

	public ImageResultArrayAdapter(Context context, List<ImageResults> images) {
		//"simple_list_item_1" XML from android adapter makes use of 'toString()' -- good for string view, 
		//but we need to convert string to image...therefore we will use our own XML and override the
		//getView() method of android Adapter
		//super(context, android.R.layout.simple_list_item_1, images);
		super(context, R.layout.item_image_result, images);
	}

	//convert item/data-source to view...in this case converting the java object/imageResult to view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);   <-- this will again just call the 'toString() way
		
		ImageResults imageInfo = this.getItem(position);
		SmartImageView ivImage;
		
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (SmartImageView)inflator.inflate(R.layout.item_image_result, parent, false);
		} else {
			ivImage = (SmartImageView)convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
	}

}
