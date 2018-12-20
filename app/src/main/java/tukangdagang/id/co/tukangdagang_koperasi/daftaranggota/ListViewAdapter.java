package tukangdagang.id.co.tukangdagang_koperasi.daftaranggota;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.R;

public class ListViewAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;

    //constructor
    public ListViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv,mdescTv;
        ImageView mIconIv;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ListViewAdapter.ViewHolder holder;
        if (view==null){
            holder = new ListViewAdapter.ViewHolder();
            view = inflater.inflate(R.layout.row_daftaranggota, null);

            holder.mTitleTv = view.findViewById(R.id.title_daftar_anggota);
            holder.mdescTv = view.findViewById(R.id.desc_daftar_anggota);
            holder.mIconIv = view.findViewById(R.id.icon_daftar_anggota);

            view.setTag(holder);

        }
        else {
            holder = (ListViewAdapter.ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mdescTv.setText(modellist.get(postition).getDesc());
        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());
        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later
                if (modellist.get(postition).getTitle().equals("Informasi Umum")){
                    //start NewActivity with title for actionbar and text for textview
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                    View mView = inflater.inflate(R.layout.activity_informasi_umum, null);
                    final EditText mEmail = (EditText) mView.findViewById(R.id.etnama_depan);
                    final EditText mPassword = (EditText) mView.findViewById(R.id.etnama_belakang);
                    Button btnSimpan = (Button) mView.findViewById(R.id.btnsimpan);


//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    btnSimpan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                Toast.makeText(mContext,
                                        "Sukses",
                                        Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });


        return view;
    }

//    //filter
//    public void filter(String charText){
//        charText = charText.toLowerCase(Locale.getDefault());
//        modellist.clear();
//        if (charText.length()==0){
//            modellist.addAll(arrayList);
//        }
//        else {
//            for (Model model : arrayList){
//                if (model.getTitle().toLowerCase(Locale.getDefault())
//                        .contains(charText)){
//                    modellist.add(model);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}