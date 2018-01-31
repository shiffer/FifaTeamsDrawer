package com.infi.teamdrawer.main.TeamsFragment;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.infi.teamdrawer.BR;
import com.infi.teamdrawer.R;
import com.infi.teamdrawer.binding.adapter.simple.model.DataBindingAdapterItemBaseVM;
import com.infi.teamdrawer.database.FtdDatabase;
import com.infi.teamdrawer.utils.JavaUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;

/**
 * Created by ohadshiffer
 * on 30/01/2018.
 */

@Table(name = TeamVM.TABLE_NAME,
        database = FtdDatabase.class,
        primaryKeyConflict = ConflictAction.IGNORE)
public class TeamVM
        extends DataBindingAdapterItemBaseVM
        implements Parcelable, Serializable {

    public static final String TABLE_NAME = "teams";

    @PrimaryKey
    @Column(name = DBColumns.TEAM_NAME)
    String mName;

    public TeamVM() {}

    public TeamVM(String name) {
        mName = name;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        if (!JavaUtils.equals(mName, name)) {
            mName = name;
            notifyPropertyChanged(BR.name);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_team;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
    }

    protected TeamVM(Parcel in) {
        this.mName = in.readString();
    }

    public static final Creator<TeamVM> CREATOR = new Creator<TeamVM>() {
        @Override
        public TeamVM createFromParcel(Parcel source) {
            return new TeamVM(source);
        }

        @Override
        public TeamVM[] newArray(int size) {
            return new TeamVM[size];
        }
    };
    //endregion

    public interface DBColumns {
        String TEAM_NAME = "teamName";
    }

}
