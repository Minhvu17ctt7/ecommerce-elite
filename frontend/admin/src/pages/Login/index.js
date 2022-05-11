
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Toast } from 'primereact/toast';
import React, { useState, useRef } from 'react';
import { useForm } from 'react-hook-form';
import { useHistory } from 'react-router-dom';
import AuthService from '../../service/AuthService';
import './FormDemo.css';
const Login = () => {
    const history = useHistory();
    const toast = useRef(null);
    const defaultValues = {
        email: '',
        password: '',
    }

    const { register, formState: { errors }, handleSubmit, reset } = useForm({ defaultValues });

    const onSubmit = async (data) => {
        try {
            const userResponse = await AuthService.login(data);
            const user = userResponse.data;
            console.log("user: ", user);
            const roles = user.roles.map(role => role.name);
            if (roles.indexOf("ADMIN") !== -1) {
                localStorage.setItem('user', JSON.stringify(userResponse.data));
                localStorage.setItem('accessToken', userResponse.data.accessToken);
                localStorage.setItem('refreshToken', userResponse.data.refreshToken);
                localStorage.setItem('isLogin', 'true');
                reset();
                history.replace("/");
            } else {
                toast.current.show({ severity: 'error', summary: 'Error', detail: 'UnAuthorization', life: 3000 });
            }
        } catch (e) {
            toast.current.show({ severity: 'error', summary: 'Error', detail: e.message, life: 3000 });
        }
    };

    return (

        <div className="modal-window">
            <div className="form-demo">
                <Toast ref={toast} />
                <div className="flex justify-content-center">
                    <div className="card">
                        <h4 className="text-center">Login Admin</h4>
                        <form onSubmit={handleSubmit(onSubmit)} className="p-fluid">
                            <div className="field">
                                <span id="email" className={errors.email ? 'p-error' : ''}>Email*</span>
                                <InputText id="email" aria-labelledby="email"
                                    className={errors.email ? 'p-invalid' : ''}
                                    placeholder="your-email@gmail.com" {...register('email', {
                                        required: 'Please enter email',
                                    })}
                                />
                                {
                                    errors.email && <small id="username2-help" className="p-error block"> {errors.email.message}</small>
                                }
                            </div>
                            <div className="field">
                                <span id="password" className={errors.password ? 'p-error' : ''} >Password*</span>
                                <InputText id="password" aria-labelledby="password"
                                    className={errors.password ? 'p-invalid' : ''}
                                    {...register("password", {
                                        required: "Please enter password",
                                    })}
                                />
                                {
                                    errors.password && <small id="username2-help" className="p-error block"> {errors.password.message}</small>
                                }
                            </div>
                            <Button type="submit" label="Login" className="mt-2" />
                        </form>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default Login