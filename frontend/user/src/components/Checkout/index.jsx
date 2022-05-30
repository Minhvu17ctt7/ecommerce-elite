import { useSnackbar } from 'notistack';
import React from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import orderApi from '../../api/orderApi';
import { getCartLoggedAction } from '../../redux/actions/cartActions';
import { formatCurrency } from '../../util/util';

const Checkout = () => {
    const { error, carts } = useSelector(state => state.cart);

    const { register, formState: { errors }, handleSubmit } = useForm();

    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();

    const onSubmit = async (data) => {
        const orderRequest = {
            "firstName": data.firstName,
            "lastName": data.lastName,
            "phone": data.phone,
            "address": data.address,
            "city": data.city,
            "country": data.country,
            "cartItemList": carts.cartItems
        };
        try {
            await orderApi.createOrder(orderRequest);
            dispatch(getCartLoggedAction());
            enqueueSnackbar("Order success", { variant: "success", autoHideDuration: 3000 });
        } catch (e) {
            enqueueSnackbar(error.message, { variant: "error", autoHideDuration: 3000 });
        }
    }

    return (
        <div className="container-fluid pt-5" >
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="row px-xl-5">
                    <div className="col-lg-8">
                        <div className="mb-4">
                            <h4 className="font-weight-semi-bold mb-4">Billing Address</h4>
                            <div className="row">
                                <div className="col-md-6 form-group">
                                    <label>First Name</label>
                                    <input className={errors.firstName ? "form-control is-invalid" : "form-control"} type="text" placeholder="John"
                                        {...register('firstName', {
                                            required: 'Please enter first name'
                                        })}
                                    />
                                    {!!errors.firstName && <div className="invalid-feedback text-left">
                                        {errors.firstName.message}
                                    </div>}
                                </div>
                                <div className="col-md-6 form-group">
                                    <label>Last Name</label>
                                    <input className={errors.lastName ? "form-control is-invalid" : "form-control"} type="text" placeholder="Doe"
                                        {...register('lastName', {
                                            required: 'Please enter last name'
                                        })}
                                    />
                                    {!!errors.lastName && <div className="invalid-feedback text-left">
                                        {errors.lastName.message}
                                    </div>}
                                </div>
                                <div className="col-md-6 form-group">
                                    <label>Mobile No</label>
                                    <input className={errors.phone ? "form-control is-invalid" : "form-control"} type="text" placeholder="+123 456 789"
                                        {...register('phone', {
                                            required: 'Please enter phone'
                                        })}
                                    />
                                    {!!errors.phone && <div className="invalid-feedback text-left">
                                        {errors.phone.message}
                                    </div>}
                                </div>
                                <div className="col-md-6 form-group">
                                    <label>Address</label>
                                    <input className={errors.address ? "form-control is-invalid" : "form-control"} type="text" placeholder="123 Street"
                                        {...register('address', {
                                            required: 'Please enter address'
                                        })} />
                                    {!!errors.address && <div className="invalid-feedback text-left">
                                        {errors.address.message}
                                    </div>}
                                </div>
                                {/* <div className="col-md-6 form-group">
                                    <label>Country</label>
                                    <select className="custom-select">
                                        <option selected>United States</option>
                                        <option>Afghanistan</option>
                                        <option>Albania</option>
                                        <option>Algeria</option>
                                    </select>
                                </div> */}
                                <div className="col-md-6 form-group">
                                    <label>Country</label>
                                    <input className={errors.country ? "form-control is-invalid" : "form-control"} type="text" placeholder="123 Street"
                                        {...register('country', {
                                            required: 'Please enter country'
                                        })} />
                                    {!!errors.country && <div className="invalid-feedback text-left">
                                        {errors.country.message}
                                    </div>}
                                </div>
                                <div className="col-md-6 form-group">
                                    <label>City</label>
                                    <input className={errors.city ? "form-control is-invalid" : "form-control"} type="text" placeholder="New York"
                                        {...register('city', {
                                            required: 'Please enter city'
                                        })}
                                    />
                                    {!!errors.city && <div className="invalid-feedback text-left">
                                        {errors.city.message}
                                    </div>}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-4">
                        <div className="card border-secondary mb-5">
                            <div className="card-header bg-secondary border-0">
                                <h4 className="font-weight-semi-bold m-0">Order Total</h4>
                            </div>
                            <div className="card-body">
                                <h5 className="font-weight-medium mb-3">Products</h5>
                                {
                                    carts.cartItems.map((cartItem, index) => (
                                        <div key={index} className="d-flex justify-content-between">
                                            <p>{cartItem.nameProduct}</p>
                                            <p>{formatCurrency(cartItem.priceProduct)}</p>
                                        </div>
                                    ))
                                }
                                <hr className="mt-0" />
                                <div className="d-flex justify-content-between mb-3 pt-1">
                                    <h6 className="font-weight-medium">Subtotal</h6>
                                    <h6 className="font-weight-medium">$150</h6>
                                </div>
                                <div className="d-flex justify-content-between">
                                    <h6 className="font-weight-medium">Shipping</h6>
                                    <h6 className="font-weight-medium">$10</h6>
                                </div>
                            </div>
                            <div className="card-footer border-secondary bg-transparent">
                                <div className="d-flex justify-content-between mt-2">
                                    <h5 className="font-weight-bold">Total</h5>
                                    <h5 className="font-weight-bold">$160</h5>
                                </div>
                            </div>
                        </div>
                        <div className="card border-secondary mb-5">
                            <div className="card-header bg-secondary border-0">
                                <h4 className="font-weight-semi-bold m-0">Payment</h4>
                            </div>
                            <div className="card-body">
                                <div className="form-group">
                                    <div className="custom-control custom-radio">
                                        <input type="radio" className="custom-control-input" name="payment" id="paypal" />
                                        <label className="custom-control-label" htmlFor="paypal">Paypal</label>
                                    </div>
                                </div>
                                <div className="form-group">
                                    <div className="custom-control custom-radio">
                                        <input type="radio" className="custom-control-input" name="payment" id="directcheck" />
                                        <label className="custom-control-label" htmlFor="directcheck">Direct Check</label>
                                    </div>
                                </div>
                                <div className>
                                    <div className="custom-control custom-radio">
                                        <input type="radio" className="custom-control-input" name="payment" id="banktransfer" />
                                        <label className="custom-control-label" htmlFor="banktransfer">Bank Transfer</label>
                                    </div>
                                </div>
                            </div>
                            <div className="card-footer border-secondary bg-transparent">
                                <button type='submit' className="btn btn-lg btn-block btn-primary font-weight-bold my-3 py-3">Place Order</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default Checkout