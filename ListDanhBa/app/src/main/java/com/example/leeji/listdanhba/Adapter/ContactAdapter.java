package com.example.leeji.listdanhba.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leeji.listdanhba.R;
import com.example.leeji.listdanhba.model.Contact;

import java.util.List;

/**
 * Created by lee ji on 23/03/2018.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> arrayContact;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arrayContact=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview,parent,false);
            viewHolder.imgAvatar=(ImageView) convertView.findViewById(R.id.img_avatar);
            viewHolder.tvName=(TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvNumber=(TextView) convertView.findViewById(R.id.tv_number);

            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        Contact contact = arrayContact.get(position);

        viewHolder.tvName.setText(contact.getmName());
        viewHolder.tvNumber.setText(contact.getmNumber());

        if(contact.isMale()){
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.ic_male2);
        }else {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.ic_female2);
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvNumber;
    }
}
