import React, { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import Header from '../../components/Layout/Header'
import Product from '../../components/Shop/Product'
import Sidebar from '../../components/Shop/Sidebar'


const titleHeader = {
    mainTitle: 'OUR SHOP',
    extraTitle: 'SHOP'
}

const Shop = () => {

    const [products, setProducts] = useState();
    const [categories, setCategories] = useState([]);
    const [searchParams] = useSearchParams();
    const categoryId = searchParams.get("categoryId");
    const sortName = searchParams.get("sortName") || "asc";
    const sortField = searchParams.get("sortField") || "name";
    const priceFrom = searchParams.get("priceFrom");
    const priceTo = searchParams.get("priceTo");
    const page = searchParams.get("page") || 1;
    const search = searchParams.get("search");

    const getStringSearch = () => {
        let searchStr = "";
        if (categoryId) {
            searchStr += `category:${categoryId},`
        }
        if (search) {
            searchStr += `name:${search};`
        }
        if (priceFrom && priceTo) {
            searchStr += `price>${priceFrom};price<${priceTo};`
        }
        return searchStr;
    }

    useEffect(() => {
        (async () => {
            const data = {
                "page": page,
                "sortField": sortField,
                "sortName": sortName,
                "pageSize": 6,
                "search": getStringSearch()
            }

            const categoriesResponse = await categoryApi.getAllCategory();
            const productsResponse = await productApi.getAllProductFilter(data);
            setProducts(productsResponse.data);
            setCategories(categoriesResponse.data);
        })()
    }, [search, page, categoryId, sortField, sortName, priceFrom, priceTo]);


    return (<>
        <Header main titleHeader={titleHeader} />
        <div className="container-fluid pt-5">
            <div className="row px-xl-5">
                <Sidebar categories={categories} />
                <Product productPagination={products} />
            </div>
        </div>
    </>)

}

export default Shop