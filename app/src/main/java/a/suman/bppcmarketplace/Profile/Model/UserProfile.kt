package a.suman.bppcmarketplace.Profile.Model

class UserProfile(
    id: Int,
    name: String,
    email: String
//hostel:String,
//contactNo:Int
) {

    private var id: Int
    private var name: String
    private var email: String
//    private var hostel: String
//    private var contactNo: Int


    init {
        this.id = id
        this.name = name
        this.email = email
//        this.hostel = hostel
//        this.contactNo = contactNo
    }

}