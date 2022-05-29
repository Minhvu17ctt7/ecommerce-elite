import { useSnackbar } from 'notistack';
import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { addToCartAction, removeFromCartAction } from '../../redux/actions/cartActions';
import { formatCurrency } from '../../util/util'

const Cart = () => {

    const { error, carts } = useSelector(state => state.cart);

    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();

    const addToCart = (cartItem) => {
        dispatch(addToCartAction(cartItem));
    }

    const removeFromCart = (cartItem) => {
        dispatch(removeFromCartAction(cartItem));
    }

    return (
        <div className="container-fluid pt-5">
            <div className="row px-xl-5">
                <div className="col-lg-8 table-responsive mb-5">
                    <table className="table table-bordered text-center mb-0">
                        <thead className="bg-secondary text-dark">
                            <tr>
                                <th>Products</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody className="align-middle">
                            {
                                carts != null && carts.cartItems.map(cartItem => (
                                    <tr>
                                        <td className="align-middle"><img src={cartItem.mainImage} alt="" style={{ width: '50px' }} /> {cartItem.nameProduct}</td>
                                        <td className="align-middle">{formatCurrency(cartItem.priceProduct)}</td>
                                        <td className="align-middle">
                                            <div className="input-group quantity mx-auto" style={{ width: '100px' }}>
                                                <div className="input-group-btn">
                                                    <button className="btn btn-sm btn-primary btn-minus"
                                                        onClick={() => removeFromCart({ ...cartItem, "quantity": 1 })}
                                                    >
                                                        <i className="fa fa-minus" />
                                                    </button>
                                                </div>
                                                <input type="text" className="form-control form-control-sm bg-secondary text-center" value={cartItem.quantity} readOnly />
                                                <div className="input-group-btn">
                                                    <button className="btn btn-sm btn-primary btn-plus" onClick={() => addToCart({ ...cartItem, "quantity": 1 })}>
                                                        <i className="fa fa-plus" />
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                        <td className="align-middle">{formatCurrency(cartItem.quantity * cartItem.priceProduct)}</td>
                                        <td className="align-middle"><button className="btn btn-sm btn-primary" onClick={() => removeFromCart(cartItem)}><i className="fa fa-times" /></button></td>
                                    </tr>
                                ))
                            }

                        </tbody>
                    </table>
                </div>
                <div className="col-lg-4">
                    <form className="mb-5 mt-0" action>
                        <div className="input-group">
                            <input type="text" className="form-control p-4" placeholder="Coupon Code" />
                            <div className="input-group-append">
                                <button className="btn btn-primary">Apply Coupon</button>
                            </div>
                        </div>
                    </form>
                    <div className="card border-secondary mb-5">
                        <div className="card-header bg-secondary border-0">
                            <h4 className="font-weight-semi-bold m-0">Cart Summary</h4>
                        </div>
                        <div className="card-body">
                            <div className="d-flex justify-content-between mb-3 pt-1">
                                <h6 className="font-weight-medium">Total price</h6>
                                <h6 className="font-weight-medium">{formatCurrency(carts.totalPrice)}</h6>
                            </div>
                            <div className="d-flex justify-content-between">
                                <h6 className="font-weight-medium">Total Item</h6>
                                <h6 className="font-weight-medium">{carts.totalItem}</h6>
                            </div>
                        </div>
                        <div className="card-footer border-secondary bg-transparent">
                            <div className="d-flex justify-content-between mt-2">
                                <h5 className="font-weight-bold">Total</h5>
                                <h5 className="font-weight-bold">{formatCurrency(carts.totalPrice)}</h5>
                            </div>
                            <Link to="/checkout">
                                <button className="btn btn-block btn-primary my-3 py-3" disabled={carts?.cartItems.length == 0} >Proceed To Checkout</button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Cart