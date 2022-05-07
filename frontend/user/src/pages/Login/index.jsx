import React from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { REG_EMAIL, REG_PASSWORD } from '../../constant/globalConstant';
import { loginUserAction } from '../../redux/actions/authenticationActions';
import "./style.css"

const Login = () => {
    const dispatch = useDispatch();
    const navigation = useNavigate();
    const { register, formState: { errors }, handleSubmit } = useForm();

    const { isLoading, user, error, isLogin } = useSelector(state => state.login);
    if (isLogin) {
        navigation("/");
    }

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
                                            required: 'Please enter email',
                                            pattern: {
                                                value: REG_EMAIL,
                                                message: 'Email is wrong format',
                                            },
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
                                <input type="submit" value="Log In" className="btn btn-block btn-primary" />
                                {error != null && (<p>{error.message}</p>)}
                            </form>
                        </div>
                    </div>
                </div>
            </div>
           
        </div>
    );
}

export default Login