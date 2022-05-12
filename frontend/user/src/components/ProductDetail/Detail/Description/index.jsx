import React, { useCallback, useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { Rating } from 'react-simple-star-rating';
import reviewApi from '../../../../api/reviewApi';
import Pagination from '../../../Pagination';

const Description = ({ product, reviewPagination, forceFetchData }) => {
    const navigate = useNavigate();
    const [rating, setRating] = useState(0);
    const [invalidRating, setInvalidRating] = useState(false);
    const [searchParams] = useSearchParams();
    const pageReview = searchParams.get("pageReview") || 1;
    const { id } = useParams();

    const { register, formState: { errors }, handleSubmit, watch } = useForm();

    const handleRating = (rate) => {
        setRating(rate)
        setInvalidRating(false);
    }

    const onSubmit = async (data) => {
        const dataReview = {
            "productId": product.id,
            "rating": rating / 20,
            "comment": data.review
        }
        if (rating / 20 === 0) {
            setInvalidRating(true);
        } else {
            await reviewApi.createReview(dataReview);
            forceFetchData();
        }
    }

    const handleNextPage = (nextPage) => {
        let url = `/products/${id}?pageReview=${nextPage}`;
        navigate(url);
    }

    const isLogin = localStorage.getItem("isLogin") === 'true';
    return (

        <div className="row px-xl-5">
            <div className="col">
                <div className="nav nav-tabs justify-content-center border-secondary mb-4">
                    <a className="nav-item nav-link active" data-toggle="tab" href="#tab-pane-1">Description</a>
                    <a className="nav-item nav-link" data-toggle="tab" href="#tab-pane-2">Information</a>
                    <a className="nav-item nav-link" data-toggle="tab" href="#tab-pane-3">Reviews ({product?.reviews.length})</a>
                </div>
                <div className="tab-content">
                    <div className="tab-pane fade show active" id="tab-pane-1">
                        <h4 className="mb-3">Product Description</h4>
                        <p>{product?.fullDescription}</p>
                    </div>
                    <div className="tab-pane fade" id="tab-pane-2">
                        <h4 className="mb-3">Additional Information</h4>
                        <p>Eos no lorem eirmod diam diam, eos elitr et gubergren diam sea. Consetetur vero aliquyam invidunt duo dolores et duo sit. Vero diam ea vero et dolore rebum, dolor rebum eirmod consetetur invidunt sed sed et, lorem duo et eos elitr, sadipscing kasd ipsum rebum diam. Dolore diam stet rebum sed tempor kasd eirmod. Takimata kasd ipsum accusam sadipscing, eos dolores sit no ut diam consetetur duo justo est, sit sanctus diam tempor aliquyam eirmod nonumy rebum dolor accusam, ipsum kasd eos consetetur at sit rebum, diam kasd invidunt tempor lorem, ipsum lorem elitr sanctus eirmod takimata dolor ea invidunt.</p>
                        <div className="row">
                            <div className="col-md-6">
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item px-0">
                                        Sit erat duo lorem duo ea consetetur, et eirmod takimata.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Amet kasd gubergren sit sanctus et lorem eos sadipscing at.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Duo amet accusam eirmod nonumy stet et et stet eirmod.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Takimata ea clita labore amet ipsum erat justo voluptua. Nonumy.
                                    </li>
                                </ul>
                            </div>
                            <div className="col-md-6">
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item px-0">
                                        Sit erat duo lorem duo ea consetetur, et eirmod takimata.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Amet kasd gubergren sit sanctus et lorem eos sadipscing at.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Duo amet accusam eirmod nonumy stet et et stet eirmod.
                                    </li>
                                    <li className="list-group-item px-0">
                                        Takimata ea clita labore amet ipsum erat justo voluptua. Nonumy.
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div className="tab-pane fade" id="tab-pane-3">
                        <div className="row">
                            <div className="col-md-6">
                                <h4 className="mb-4">{product?.reviews.length} review for "Colorful Stylish Shirt"</h4>
                                {reviewPagination && reviewPagination?.reviews.map(review => (
                                    <div className="media mb-4">
                                        <img src="/public/img/user-default.png" alt="Image" className="img-fluid mr-3 mt-1" style={{ width: '45px' }} />
                                        <div className="media-body">
                                            <h6>{review.user.firstName} {review.user.lastName}<small> - <i>01 Jan 2045</i></small></h6>
                                            <div className="text-primary mb-2">
                                                <Rating readonly ratingValue={review.rating * 20} />
                                            </div>
                                            <p>{review.comment}</p>
                                        </div>
                                    </div>
                                ))}
                                {reviewPagination?.totalPage > 1 && (<Pagination
                                    totalPage={parseInt(reviewPagination?.totalPage)}
                                    currentPage={parseInt(pageReview)}
                                    handleNextPage={handleNextPage}
                                />)}
                                {/* <Pagination totalPage={parseInt(reviewPagination?.totalPage)} currentPage={pageReview} handl /> */}
                            </div>
                            <div className="col-md-6">
                                {isLogin ? (<> <h4 className="mb-4">Leave a review</h4>
                                    <div className="d-flex my-3">
                                        <p className="mb-0 mr-2 d-flex align-items-center">Your Rating * :</p>
                                        <Rating onClick={handleRating} ratingValue={rating} />
                                    </div>
                                    {invalidRating && (<p style={{ color: 'red', textAlign: 'left' }}>Please rating!</p>)}
                                    <form onSubmit={handleSubmit(onSubmit)}>
                                        <div className="form-group">
                                            <label htmlFor="message">Your Review *</label>
                                            <textarea id="message" cols={30} rows={5} className={errors.review ? "form-control is-invalid" : "form-control"} defaultValue={""}
                                                {...register("review", { required: "Review is required!" })} />
                                            {!!errors.review && <div className="invalid-feedback text-left">
                                                {errors.review.message}</div>}
                                        </div>
                                        <div className="form-group mb-0">
                                            <input type="submit" defaultValue="Leave Your Review" className="btn btn-primary px-3" />
                                        </div>
                                    </form></>
                                ) : (<Link to="/login"><input type="button" value="Login to review" className="btn btn-primary px-3" /></Link>)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Description