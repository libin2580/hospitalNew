package com.zulekhahospitals.zulekhaapp.imagecapture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.zulekhahospitals.zulekhaapp.R;

import java.io.File;

public class Gallery extends AppCompatActivity {
private GridView gridView;
    private static final String IMAGE_DIRECTORY_NAME="camera_pics";
    private GridViewAdapter gridAdapter;
    String fileLoc;
    String imgName;
    TouchImageView imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imageItem=(TouchImageView) findViewById(R.id.camera_pic);


        File directory=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        File[] contents=directory.listFiles();


        if (contents == null) {

        }
// Folder is empty
        else if (contents.length == 0) {

        }
// Folder contains files
        else {

            fileLoc=contents[contents.length-1].toString();
            imgName=contents[contents.length-1].toString().substring(41,contents[contents.length-1].toString().length()-1);

            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inSampleSize = 8;
            Bitmap bitmap2 = BitmapFactory.decodeFile(fileLoc, options1);

            imageItem.setImageBitmap(bitmap2);

        }

        /*gridView=(GridView)findViewById(R.id.gridView1);
        gridAdapter=new GridViewAdapter(this,R.layout.grid_item_layout,getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ImageItem item=(ImageItem)parent.getItemAtPosition(position);
                Intent intent = new Intent(Gallery.this, DetailsActivity.class);
                intent.putExtra("title", item.getImg_name());
                intent.putExtra("image", item.getImage());

            }
        });*/

    }

   /* private ArrayList<ImageItem>getData(){

        ArrayList<ImageItem>imageItems=new ArrayList<>();
        ImageItem imgmodel;


        /////////////////getting from directory----begin //////////////

        File directory=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
        File[] contents=directory.listFiles();


        if (contents == null) {

        }
// Folder is empty
        else if (contents.length == 0) {

        }
// Folder contains files
        else {

            imgmodel=new ImageItem();
            imgmodel.image=contents[contents.length-1].toString();
            imgmodel.img_name=contents[contents.length-1].toString().substring(41,contents[contents.length-1].toString().length()-1);
            imageItems.add(imgmodel);


            *//*for(int i=0;i<contents.length;i++){
                System.out.println(contents[i]+"\n");
                System.out.println("***** image name  : "+contents[i].toString().substring(41,contents[i].toString().length()-1));

                imgmodel=new ImageItem();
               imgmodel.image=contents[i].toString();
                imgmodel.img_name=contents[i].toString().substring(41,contents[i].toString().length()-1);
                imageItems.add(imgmodel);
            }*//*
        }
        /////////////////getting from directory----end //////////////

return imageItems;
    }*/


}
