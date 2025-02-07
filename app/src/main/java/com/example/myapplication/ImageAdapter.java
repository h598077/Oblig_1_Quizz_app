package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    private List<Integer> imageResources;
    private List<String> imageTexts;

    final String[] imageNames = {"Dog", "Chicken"};
    public ImageAdapter(Context context, List<Integer> imageResources, List<String> imageTexts) {
        this.context = context;
        this.imageResources = imageResources;
        this.imageTexts = imageTexts;
    }
    private int[] showAddImageDialog() {
        // List of available images from drawable
        final int[] availableImages = {
                R.drawable.dog,
                R.drawable.chicken,
        };
        return availableImages;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResources.get(position));
        holder.textView.setText(imageTexts.get(position));

        // Handle click event on image
        holder.imageView.setOnClickListener(v -> showDeleteDialog(position));
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public void sortImages(final boolean ascending) {
        List<Pair<String, Integer>> sortedList = new ArrayList<>();

        for (int i = 0; i < imageTexts.size(); i++) {
            sortedList.add(new Pair<>(imageTexts.get(i), imageResources.get(i)));
        }

        // Sort list by imageTexts (A-Z or Z-A)
        Collections.sort(sortedList, (o1, o2) ->
                ascending ? o1.first.compareTo(o2.first) : o2.first.compareTo(o1.first)
        );

        // Update lists
        imageTexts.clear();
        imageResources.clear();
        for (Pair<String, Integer> pair : sortedList) {
            imageTexts.add(pair.first);
            imageResources.add(pair.second);
        }

        notifyDataSetChanged(); // Refresh RecyclerView
    }




    public void addImage() {
        new AlertDialog.Builder(context)
                .setTitle("Choose an Image to Add")
                .setItems(imageNames, (dialog, which) -> {
                    // User selected an image from the list
                    int selectedImage = showAddImageDialog()[which];
                    String selectedText = imageNames[which];

                    // Add selected image to RecyclerView
                    imageResources.add(selectedImage);
                    imageTexts.add(selectedText);
                    notifyItemInserted(imageResources.size() - 1);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // Remove item from RecyclerView
    private void removeImage(int position) {
        if (position >= 0 && position < imageResources.size()) {
            imageResources.remove(position); // Remove picture
            imageTexts.remove(position);   // Remove text
            notifyItemRemoved(position); // Tell recycleview item is removed
            notifyItemRangeChanged(position, imageResources.size()); // Updates remaining items so their position is correct (if you delete image 2 at index 1 image 3 gets index 1)
        }
    }

    // Show a confirmation dialog before deleting
    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete", (dialog, which) -> removeImage(position))
                .setNegativeButton("Cancel", null)
                .show();
    }


    // holds imageview and textview for each view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

}