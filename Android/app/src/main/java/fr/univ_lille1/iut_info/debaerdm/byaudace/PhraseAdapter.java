package fr.univ_lille1.iut_info.debaerdm.byaudace;

import android.app.Activity;
import android.content.Context;
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
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (PhraseHolder)row.getTag();
        }

        Phrase pm = data.get(position);

        holder.txtTitle.setText(pm.toString());

        System.out.println(pm.getCategorie());

        if(pm.getCategorie() == "Marché")
            holder.imgIcon.setImageResource(R.drawable.marche);
        else if(pm.getCategorie() == "Technique")
            holder.imgIcon.setImageResource(R.drawable.technique);
        else if(pm.getCategorie() == "Juridique")
            holder.imgIcon.setImageResource(R.drawable.justice);
        else if(pm.getCategorie() == "Ressources humaines")
            holder.imgIcon.setImageResource(R.drawable.rh);
        else if(pm.getCategorie() == "Partenariat")
            holder.imgIcon.setImageResource(R.drawable.partenariat);
        else
            holder.imgIcon.setImageResource(R.drawable.defaut);

        return row;
    }

    static class PhraseHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
