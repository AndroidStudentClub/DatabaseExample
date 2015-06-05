package com.example.admin.personallibrarycatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.personallibrarycatalogue.data.Book;

import java.util.List;

/**
 * Created by Admin on 27.05.2015.
 */
public class BooksListAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder {
        TextView authorTextView_;
        TextView titleTextView_;
        TextView descriptionTextView_;
        ImageView coverImageView_;
    }

    public BooksListAdapter(Context context, List<Book> booksList) {
        super(context, R.layout.list_item, booksList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.authorTextView_ = (TextView) convertView.findViewById(R.id.list_item_author);
            viewHolder.titleTextView_ = (TextView) convertView.findViewById(R.id.list_item_title);
            viewHolder.coverImageView_ = (ImageView) convertView.findViewById(R.id.list_item_image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.authorTextView_.setText(getItem(position).getAuthor());

        viewHolder.titleTextView_.setText(getItem(position).getTitle());

        if (Util.getBitmapFromBytes(getItem(position).getCover()) != null) {
            viewHolder.coverImageView_.setImageBitmap(Util.getBitmapFromBytes(getItem(position).getCover()));
        }

        return convertView;
    }


}
