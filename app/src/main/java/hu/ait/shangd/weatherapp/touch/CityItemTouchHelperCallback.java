package hu.ait.shangd.weatherapp.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import hu.ait.shangd.weatherapp.adapter.CityRecyclerAdapter;

public class CityItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private CityRecyclerAdapter adapter;

    public CityItemTouchHelperCallback(CityRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder) {

        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeCity(viewHolder.getAdapterPosition());
    }
}
