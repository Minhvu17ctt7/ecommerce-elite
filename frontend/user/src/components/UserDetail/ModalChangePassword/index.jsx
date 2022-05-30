import React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { REG_PASSWORD } from '../../../constant/globalConstant';

function ModalChangePassword({ show, handleClose, onSubmit }) {
     const { register, formState: { errors }, handleSubmit, watch } = useForm();

     return (
          <>
               <Modal show={show} onHide={handleClose} centered>
                    <form method="post" onSubmit={handleSubmit(onSubmit)}>
                         <Modal.Header closeButton>
                              <Modal.Title>Update Password</Modal.Title>
                         </Modal.Header>
                         <Modal.Body>
                              <div className="form-group mb-3">
                                   <div className="form-label">
                                        <label>Old Password</label>
                                   </div>
                                   <input type="password" className={errors.oldPassword ? "form-control is-invalid" : "form-control"} placeholder="Your Password" id="password"
                                        {...register("oldPassword",
                                             {
                                                  required: "Password is required!",
                                                  pattern: {
                                                       value: REG_PASSWORD,
                                                       message: 'Password is wrong format'
                                                  }
                                             })} />
                                   {!!errors.oldPassword && <div className="invalid-feedback text-left">
                                        {errors.oldPassword.message}
                                   </div>}
                              </div>
                              <div className="form-group mb-3">
                                   <div className="form-label">
                                        <label>New Password</label>
                                   </div>
                                   <input type="password" className={errors.newPassword ? "form-control is-invalid" : "form-control"} placeholder="Your Password" id="password"
                                        {...register("newPassword",
                                             {
                                                  required: "New password is required!",
                                                  pattern: {
                                                       value: REG_PASSWORD,
                                                       message: 'New Password is wrong format'
                                                  }
                                             })} />
                                   {!!errors.newPassword && <div className="invalid-feedback text-left">
                                        {errors.newPassword.message}
                                   </div>}
                              </div>

                              <div className="form-group mb-3">
                                   <div className="form-label">
                                        <label>Password repeat</label>
                                   </div>
                                   <input type="password" className={errors.repeatPassword ? "form-control is-invalid" : "form-control"} placeholder="Your Password" id="repeat-password"
                                        {...register("repeatPassword",
                                             {
                                                  required: "Repeat password is required!",
                                                  validate: value => value === watch('newPassword') || "The passwords do not match"
                                             })} />
                                   {!!errors.repeatPassword && <div className="invalid-feedback text-left">
                                        {errors.repeatPassword.message}
                                   </div>}
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


export default ModalChangePassword