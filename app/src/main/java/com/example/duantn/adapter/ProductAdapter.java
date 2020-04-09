package com.example.duantn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.R;
import com.example.duantn.activity.model.Product;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> productList;
    ProductListener productListener;

    public ProductAdapter(List<Product> list){
        this.productList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null, false);
        return new ViewHolder(view);
    }

    public void setProductListener(ProductListener productListener){
        this.productListener = productListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());

        if(productListener != null){
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productListener.editClick(position);
                }
            });

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productListener.deleteClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        TextView tvName;

        Button btnDelete, btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imv);
            tvName = itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    public interface ProductListener{
        void editClick(int i);
        void deleteClick(int i);
    }
}
