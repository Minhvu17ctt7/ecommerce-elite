import { useSnackbar } from 'notistack';
import React, { useEffect, useLayoutEffect, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { loginUserAction, refreshUserAction } from '../../redux/actions/authenticationActions';
import { getCartLoggedAction } from '../../redux/actions/cartActions';
import "./style.css";

const Login = () => {
    const dispatch = useDispatch();
    const navigation = useNavigate();
    const { register, formState: { errors }, handleSubmit } = useForm();
    const { enqueueSnackbar } = useSnackbar();
    const isLogin = localStorage.getItem("isLogin") === "true"
    const [firstUpdate, setFirstUpdate] = useState(true);

    const { isLoading, error } = useSelector(state => state.login);

    useLayoutEffect(() => {
        if (firstUpdate) {
            dispatch(refreshUserAction());
            setFirstUpdate(false)
            return;
        } else {
            if (error !== null) {
                enqueueSnackbar(error.message, { variant: "error", autoHideDuration: 3000 });
            }
            if (isLogin) {
                enqueueSnackbar("Login success", { variant: "success", autoHideDuration: 3000 });
                navigation("/");
                dispatch(getCartLoggedAction());
            }
        }
    }, [error, isLogin])

    const onSubmit = (data) => {
        dispatch(loginUserAction(data));
    }

    return (
        <div className="d-lg-flex half">
            <div className="bg order-1 order-md-2" style={{ backgroundImage: 'url("img/bg_1.jpg")' }} />
            <div className="contents order-2 order-md-1">
                <div className="container">
                    <div className="row align-items-center justify-content-center">
                        <div className="col-md-7">
                            <h3>Login to <strong>MOSHOPPING</strong></h3>
                            {/* <p className="mb-4">Lorem ipsum dolor sit amet elit. Sapiente sit aut eos consectetur adipisicing.</p> */}
                            <form method="post" onSubmit={handleSubmit(onSubmit)}>
                                <div className="form-group first">
                                    <div className="form-label">
                                        <label>Email</label>
                                    </div>
                                    <input type="text" className="form-control is-invalid" placeholder="your-email@gmail.com" id="email"
                                        {...register('email', {
                                            required: 'Please enter email'
                                        })}

                                    />
                                    {!!errors.email && <div className="invalid-feedback text-left">
                                        {errors.email.message}
                                    </div>}
                                </div>
                                <div className="form-group last mb-3">
                                    <div className="form-label">
                                        <label>Password</label>
                                    </div>
                                    <input type="password" className="form-control is-invalid" placeholder="Your Password" id="password"
                                        {...register("password", {
                                            required: "Please enter password",
                                        })} />
                                    {!!errors.password && <div className="invalid-feedback text-left">
                                        Password is required!
                                    </div>}
                                </div>
                                <div className="d-flex mb-5 align-items-center">
                                    <span className="mr-auto"><Link to="/" className="forgot-pass">Back to home</Link></span>
                                    <span className="ml-auto"><Link to="/register" className="forgot-pass">Register</Link></span>
                                </div>
                                {isLoading && (<button type="submit" className="btn btn-block btn-primary" disabled>
                                    <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                                </button>)}
                                {!isLoading && (<input type="submit" value="Log In" className="btn btn-block btn-primary" />)}
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default Login