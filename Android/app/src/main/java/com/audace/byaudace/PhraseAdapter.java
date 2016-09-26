package com.audace.byaudace;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rémy on 13/05/2016.
 */
public class PhraseAdapter extends ArrayAdapter<Phrase> {

    Context context;
    int layoutResourceId;
    List<Phrase> data = null;

    public PhraseAdapter(Context context, int layoutResourceId, List<Phrase> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PhraseHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PhraseHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.imgReport = (ImageView)row.findViewById(R.id.imgReport);
            holder.txtPhrase = (TextView)row.findViewById(R.id.txtPhrase);
            holder.txtBesoin = (TextView)row.findViewById(R.id.txtBesoin);

            row.setTag(holder);
        }
        else
        {
            holder = (PhraseHolder)row.getTag();
        }

        final Phrase pm = data.get(position);
        holder.txtPhrase.setText(Html.fromHtml(pm.getPhrase()).toString());
        holder.txtBesoin.setText(Html.fromHtml(pm.getBesoin()).toString());

        System.out.println(pm.getCategorie());

        if(pm.getCategorie() == 0) {
            holder.imgIcon.setImageResource(R.drawable.justice);
        }else if(pm.getCategorie() == 1) {
            holder.imgIcon.setImageResource(R.drawable.marche);
        }else if(pm.getCategorie() == 2) {
            holder.imgIcon.setImageResource(R.drawable.partenariat);
        }else if(pm.getCategorie() == 3) {
            holder.imgIcon.setImageResource(R.drawable.rh);
        }else if(pm.getCategorie() == 4) {
            holder.imgIcon.setImageResource(R.drawable.technique);
        } else {
            holder.imgIcon.setImageResource(R.drawable.defaut);
        }

        // Image pour la fonction de signalement
        holder.imgReport.setImageResource(R.drawable.contact);
        holder.imgReport.setVisibility(View.GONE);

        return row;
    }

    static class PhraseHolder
    {
        ImageView imgIcon;
        ImageView imgReport;
        TextView txtPhrase;
        TextView txtBesoin;
    }

}
