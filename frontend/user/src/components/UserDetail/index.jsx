import React from 'react'

const UserDetail = ({ user }) => {
     return (
          <div className="container-fluid pt-5">
               <div className="text-center mb-4">
                    <h2 className="section-title px-5"><span className="px-2">Information user</span></h2>
               </div>
               <div className="row px-xl-5">
                    <div className="col-lg-4 mb-5">
                         <div id="product-carousel" className="carousel slide" data-ride="carousel">
                              <div className="carousel-inner border">
                                   <div className="carousel-item active">
                                        <img className="w-100 h-100" src={user.mainImage || "/public/img/user-default.png"} alt="Image" />
                                   </div>
                              </div>
                         </div>
                    </div>
                    <div className="col-lg-1 mb-5"></div>
                    <div className="col-lg-7 text-left mb-5">
                         <h5 className="font-weight-semi-bold mb-3">Email</h5>
                         <p>{user.email}</p>
                         <h5 className="font-weight-semi-bold mb-3">First Name</h5>
                         <p>{user.firstName}</p>
                         <h5 className="font-weight-semi-bold mb-3">Last Name</h5>
                         <p>{user.lastName}</p>
                         <div className="d-flex align-items-center mt-5">
                              <button className="btn btn-block btn-primary mr-2">
                                   Update user
                              </button>
                              <button className="btn btn-block btn-primary mt-0">
                                   Change password
                              </button>
                         </div>
                    </div>
               </div>
          </div>
     );
}

export default UserDetail