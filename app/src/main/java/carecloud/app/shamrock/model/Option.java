
package carecloud.app.shamrock.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Option implements Parcelable {

    @SerializedName("languageId")
    @Expose
    public Integer languageId;
    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("iconId")
    @Expose
    public Object iconId;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("child")
    @Expose
    public List<Object> child = new ArrayList<Object>();
    @SerializedName("isDefault")
    @Expose
    public Boolean isDefault;
    @SerializedName("skip")
    @Expose
    public List<Object> skip = new ArrayList<Object>();

    protected Option(Parcel in) {
        label = in.readString();
        value = in.readString();
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel in) {
            return new Option(in);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeString(value);
    }
}
