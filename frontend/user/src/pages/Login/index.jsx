import React from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { loginUserAction } from '../../redux/actions/authenticationActions';
import "./style.css"

const Login = () => {
    const dispatch = useDispatch();
    const { register, formState: { errors }, handleSubmit } = useForm();

    const userLogin = useSelector(state => state.login);

    console.log("userLogin: ", userLogin);

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
                                        <label>Username</label>
                                    </div>
                                    <input type="text" className="form-control" placeholder="your-email@gmail.com" id="username" {...register("email", { required: true })} />
                                    {errors.email?.type === 'required' && <div className="invalid text-left">
                                        Please enter a message in the textarea.
                                    </div>}
                                </div>
                                <div className="form-group last mb-3">
                                    <div className="form-label">
                                        <label>Password</label>
                                    </div>
                                    <input type="password" className="form-control" placeholder="Your Password" id="password" {...register("password", { required: true })} />
                                    {errors.password?.type === 'required' && <div className="invalid text-left">
                                        Please enter a message in the textarea.
                                    </div>}
                                </div>
                                <div className="d-flex mb-5 align-items-center">
                                    <span className="mr-auto"><Link to="#" className="forgot-pass">Register</Link></span>
                                    <span className="ml-auto"><Link to="#" className="forgot-pass">Forgot Password</Link></span>
                                </div>
                                <input type="submit" defaultValue="Log In" className="btn btn-block btn-primary" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login