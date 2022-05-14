import { useSnackbar } from 'notistack'
import React, { useEffect, useState } from 'react'
import { useParams, useSearchParams } from 'react-router-dom'
import productApi from '../../api/productApi'
import reviewApi from '../../api/reviewApi'
import Header from '../../components/Layout/Header'
import Description from '../../components/ProductDetail/Detail/Description'
import ImageProduct from '../../components/ProductDetail/Detail/Image'
import Option from '../../components/ProductDetail/Detail/Option'
import Recommend from '../../components/ProductDetail/Recommend'

const titleHeader = {
    mainTitle: 'SHOP DETAIL',
    extraTitle: 'Shop Detail'
}


const ProductDetail = () => {
    const [countFetchData, setCountFetchDate] = useState(0);
    const { id } = useParams();
    const [product, setProduct] = useState();
    const [reviews, setReviews] = useState();
    const [searchParams] = useSearchParams();
    const pageReview = searchParams.get("pageReview") || 1;
    const [isLoading, setIsLoading] = useState(false);
    const { enqueueSnackbar } = useSnackbar();

    const forceFetchData = () => {
        setCountFetchDate(preState => preState + 1);
    }

    useEffect(() => {

        (async () => {
            setIsLoading(true);
            try {
                const productResponse = await productApi.getProductById(id);
                const reviewResponse = await reviewApi.getReviews(id, pageReview);

                await setProduct(productResponse.data);
                await setReviews(reviewResponse.data);
            } catch (error) {
                enqueueSnackbar(error.message, { variant: "error", autoHideDuration: 3000 });
            }
            setIsLoading(false);
        })()
    }, [pageReview, countFetchData]);
    return (
        <>
            <Header titleHeader={titleHeader} />
            <div className="container-fluid py-5">
                <div className="row px-xl-5">
                    <ImageProduct image={product?.mainImage} />
                    <Option product={product} />
                </div>
                <Description reviewPagination={reviews} product={product} forceFetchData={forceFetchData} />
            </div>
            <Recommend />
        </>
    )
}

export default ProductDetail