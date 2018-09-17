# ViewHolderPassData
Sample Demonstrates how to pass Data between two different ViewHolder which results in either cases.  <br />
* Notify from one adapter to another that something has changed.
* Pass result from second adapter to first adapter. <br />

This sample code contains 4 files :
* MyCustomParentAdapter
* ChildAdapter
* MainActivity
* CustomModel


## MainActivity ##
 Responsible for loading dummy data from data.json present inside assets folder. <br/> Source can be REST API also depending on requirement.
 Contains 2 RecyclerView
 

## MyCustomParentAdapter ##
 Responsible for loading parent views. <br />
     
    private OnClickParentHeader mListener;

    public interface OnClickParentHeader {
        void changeChildItems(List<CustomModel> items);
    }
    
     public MyCustomParentAdapter(Context ctx,
                                 HashMap<String, String> hMapHeaderAndCount,
                                 LinkedHashMap<String, List<CustomModel>> lstData,
                                 OnClickParentHeader listener) {
        this.mListener = listener;
        this.ctx = ctx;
        this.lstData = lstData;
        this.hMapHeaderAndCount = hMapHeaderAndCount;
        this.lstKey = getKeys();
    }

* OnClickParentHeader <br />
CallBack Interface between Adapter & MainActivity.

* HashMap<String, String> hMapHeaderAndCount <br />
  HashMap <K,V> where K is Key (Title) displayed in 1st list on left side ,V is the value which will maintain the count of items selected in 2nd list.<br />
  
* LinkedHashMap<String, List<CustomModel>> lstData <br />
  LinkedHashMap<K,V> where K is Key (Title) displayed in 1st list on left side, V is the value in this case List<CustomModel>
  
## ChildAdapter ##
 Responsible for loading child views. <br />
 
    public interface ListenerCountCallback {
        void notifyParentAdapter(int count);
    }
    
    public ChildAdapter(Context ctx,List<CustomModel> lstChild,ListenerCountCallback adapter) {
        this.mListener = adapter;
        this.lstChild = lstChild;
        this.ctx = ctx;
    }

 
