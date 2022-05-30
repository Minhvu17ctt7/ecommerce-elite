import React, { useState } from 'react'
import { Button, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { REG_EMAIL } from '../../../constant/globalConstant';
import uploadImage from '../../../firebase/upload';

function ModalChangeUser({ user, show, handleClose, onSubmit }) {
     const [avatar, setAvatar] = useState(null);
     const [mainAvatar, setMainAvatar] = useState(user?.photo);
     const { register, formState: { errors }, handleSubmit, watch } = useForm({
          defaultValues: {
               id: user?.id,
               firstName: user?.firstName,
               lastName: user?.lastName,
               email: user?.email
          }
     });

     const handleChangeAvatar = (e) => {
          setAvatar(e.target.files[0]);
          const objectUrl = URL.createObjectURL(e.target.files[0]);
          setMainAvatar(objectUrl);
     }

     const handleOnSubmit = async (data) => {
          if (avatar != null) {
               const urlImage = await uploadImage("users", avatar);
               data = { ...data, "photo": urlImage };
          }
          onSubmit(data);
     }

     return (
          <>
               <Modal show={show} onHide={handleClose} centered>
                    <form method="post" onSubmit={handleSubmit(handleOnSubmit)}>
                         <Modal.Header closeButton>
                              <Modal.Title>Update User</Modal.Title>
                         </Modal.Header>
                         <Modal.Body>
                              <div className="form-group" >
                                   <img src={mainAvatar ? mainAvatar : "/public/img/user-default.png"} alt={mainAvatar} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />
                                   <input type="file" onChange={handleChangeAvatar} />
                                   <div className="form-label">
                                        <label>Email</label>
                                   </div>
                                   <input type="text" className={errors.email ? "form-control is-invalid" : "form-control"} placeholder="your-email@gmail.com" id="email" {...register('email', {
                                        required: 'Email is required',
                                        pattern: {
                                             value: REG_EMAIL,
                                             message: 'Email is wrong format',
                                        },
                                   })} readOnly />
                                   {!!errors.email && <div className="invalid-feedback text-left">
                                        {errors.email.message}
                                   </div>}
                              </div>
                              <div className="form-row">
                                   <div className="col-md-6 mb-3">
                                        <div className="form-label">
                                             <label>First name</label>
                                        </div>
                                        <input type="text" className={errors.firstName ? "form-control is-invalid" : "form-control"} placeholder="your-first-name" id="first-name"
                                             {...register("firstName", { required: "First name is required!" })} />
                                        {!!errors.firstName && <div className="invalid-feedback text-left">
                                             {errors.firstName.message}
                                        </div>}
                                   </div>
                                   <div className="col-md-6 mb-3">
                                        <div className="form-label">
                                             <label>Last name</label>
                                        </div>
                                        <input type="text" className={errors.lastName ? "form-control is-invalid" : "form-control"} placeholder="your-last-name" id="last-name"
                                             {...register("lastName", { required: "Last name is required!" })} />
                                        {!!errors.lastName && <div className="invalid-feedback text-left">
                                             {errors.lastName.message}
                                        </div>}
                                   </div>
                              </div>

                         </Modal.Body>
                         <Modal.Footer>
                              <Button variant="secondary" onClick={handleClose}>
                                   Close
                              </Button>
                              <Button variant="primary" type='submit'>
                                   Update
                              </Button>
                         </Modal.Footer>
                    </form>
               </Modal>
          </>
     );
}


export default ModalChangeUser