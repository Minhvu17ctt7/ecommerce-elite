import { useSnackbar } from 'notistack';
import React, { useEffect, useLayoutEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { REG_EMAIL, REG_PASSWORD } from '../../constant/globalConstant';
import { refreshRegisterAction, registerUserAction } from '../../redux/actions/authenticationActions';
import "./style.css"

const Register = () => {
    const dispatch = useDispatch();
    const navigation = useNavigate();
    const { register, formState: { errors }, handleSubmit, watch } = useForm();
    const { enqueueSnackbar } = useSnackbar();
    const { isLoading, error, user } = useSelector(state => state.register);

    if (user) {
        navigation("/login")
    }

    const [firstUpdate, setFirstUpdate] = useState(true);


    useLayoutEffect(() => {
        if (firstUpdate) {
            dispatch(refreshRegisterAction());
            setFirstUpdate(false)
            return;
        } else {
            if (error) {
                enqueueSnackbar(error.message, { variant: "error", autoHideDuration: 3000 });
            }
            if (user) {
                enqueueSnackbar("Register success", { variant: "success", autoHideDuration: 3000 });
            }
        }
    }, [error, user])

    const onSubmit = (data) => {
        dispatch(registerUserAction(data));
    }

    return (

        <div className="d-lg-flex half">
            <div className="bg order-1 order-md-2" style={{ backgroundImage: 'url("img/bg_1.jpg")' }} />
            <div className="contents order-2 order-md-1">
                <div className="container">
                    <div className="row align-items-center justify-content-center">
                        <div className="col-md-7">
                            <h3>Register to <strong>MOSHOPPING</strong></h3>
                            {/* <p className="mb-4">Lorem ipsum dolor sit amet elit. Sapiente sit aut eos consectetur adipisicing.</p> */}
                            <form method="post" onSubmit={handleSubmit(onSubmit)}>
                                <div className="form-group">
                                    <div className="form-label">
                                        <label>Email</label>
                                    </div>
                                    <input type="text" className="form-control is-invalid" placeholder="your-email@gmail.com" id="email" {...register('email', {
                                        required: 'Email is required',
                                        pattern: {
                                            value: REG_EMAIL,
                                            message: 'Email is wrong format',
                                        },
                                    })} />
                                    {!!errors.email && <div className="invalid-feedback text-left">
                                        {errors.email.message}
                                    </div>}
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-label">
                                        <label>Password</label>
                                    </div>
                                    <input type="password" className="form-control is-invalid" placeholder="Your Password" id="password"
                                        {...register("password",
                                            {
                                                required: "Password is required!",
                                                pattern: {
                                                    value: REG_PASSWORD,
                                                    message: 'Password is wrong format'
                                                }
                                            })} />
                                    {!!errors.password && <div className="invalid-feedback text-left">
                                        {errors.password.message}
                                    </div>}
                                </div>


                                <div className="form-group mb-3">
                                    <div className="form-label">
                                        <label>Password repeat</label>
                                    </div>
                                    <input type="password" className="form-control is-invalid" placeholder="Your Password" id="repeat-password"
                                        {...register("repeatPassword",
                                            {
                                                required: "Repeat password is required!",
                                                validate: value => value === watch('password') || "The passwords do not match"
                                            })} />
                                    {!!errors.repeatPassword && <div className="invalid-feedback text-left">
                                        {errors.repeatPassword.message}
                                    </div>}
                                </div>
                                <div className="form-row">
                                    <div className="col-md-6 mb-3">
                                        <div className="form-label">
                                            <label>First name</label>
                                        </div>
                                        <input type="text" className="form-control is-invalid" placeholder="your-first-name" id="first-name"
                                            {...register("firstName", { required: "First name is required!" })} />
                                        {!!errors.firstName && <div className="invalid-feedback text-left">
                                            {errors.firstName.message}
                                        </div>}
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <div className="form-label">
                                            <label>Last name</label>
                                        </div>
                                        <input type="text" className="form-control is-invalid" placeholder="your-last-name" id="last-name"
                                            {...register("lastName", { required: "Last name is required!" })} />
                                        {!!errors.lastName && <div className="invalid-feedback text-left">
                                            {errors.lastName.message}
                                        </div>}
                                    </div>
                                </div>
                                <div className="form-group mb-3">
                                    <div className="form-label">
                                        <label style={{ opacity: 0.6 }}>* Password must be at least 8 characters including uppercase, lowercase letters, numbers*</label>
                                    </div>
                                </div>
                                <div className="d-flex mb-5 align-items-center">
                                    <span className="mr-auto"><Link to="/" className="forgot-pass">Back home page</Link></span>
                                    <span className="ml-auto"><Link to="/login" className="forgot-pass">Login</Link></span>
                                </div>
                                {isLoading && (<button type="submit" className="btn btn-block btn-primary" disabled>
                                    <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                                </button>)}
                                {!isLoading && (<input type="submit" value="Registry" className="btn btn-block btn-primary" />)}

                                {error && (<p>{error.message}</p>)}
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register