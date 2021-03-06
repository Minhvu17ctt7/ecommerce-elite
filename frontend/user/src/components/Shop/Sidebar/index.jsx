import React from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom';

const Sidebar = ({ categories }) => {
    const [searchParams] = useSearchParams();
    const categoryId = searchParams.get("categoryId");
    const search = searchParams.get("search") || "";
    const sortName = searchParams.get("sortName") || "";
    const sortField = searchParams.get("sortField") || "";
    const priceFrom = searchParams.get("priceFrom") || "";
    const priceTo = searchParams.get("priceTo") || "";
    const navigation = useNavigate();

    const handleFilterCategory = (id) => {
        let url = `/shop?page=1&sortField=${sortField}&sortName=${sortName}&categoryId=${id}&priceFrom=${priceFrom}&priceTo=${priceTo}&search=${search}`;
        navigation(url);
    }

    const handleFilterPrice = (from, to) => {
        let url = `/shop?page=1&sortField=${sortField}&sortName=${sortName}&categoryId=${categoryId}&priceFrom=${from}&priceTo=${to}&search=${search}`;
        navigation(url);
    }

    return (

        <div className="col-lg-3 col-md-12">
            {/* Color Start */}
            <div className="border-bottom mb-4 pb-4">
                <h5 className="font-weight-semi-bold mb-4">Filter by color</h5>
                <form>
                    {!categoryId &&
                        (<div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                            <input type="checkbox" className="custom-control-input" id="color-0" onClick={() => handleFilterCategory("")} checked name="filter-categories" />
                            <label className="custom-control-label" htmlFor="color-0">All category</label>
                        </div>)}
                    {categoryId &&
                        (<div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                            <input type="checkbox" className="custom-control-input" id="color-0" onClick={() => handleFilterCategory("")} name="filter-categories" />
                            <label className="custom-control-label" htmlFor="color-0">All category</label>
                        </div>)}
                    {
                        categories.map((category, index) => {

                            return (
                                <div key={category.id} className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                    <input type="checkbox" className="custom-control-input" id={`color-${index + 1}`} onClick={() => handleFilterCategory(category.id)} checked={categoryId != null && categoryId == category.id} name="filter-categories" />
                                    <label className="custom-control-label" htmlFor={`color-${index + 1}`}>{category.name}</label>
                                </div>)
                        })
                    }
                </form>
            </div>
            {/* Color End */}

            {/* Price Start */}
            <div className="border-bottom mb-4 pb-4">
                <h5 className="font-weight-semi-bold mb-4">Filter by price</h5>
                <form>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" defaultChecked id="price-all" value="" onClick={() => handleFilterPrice("", "")} checked={priceFrom == ""} />
                        <label className="custom-control-label" htmlFor="price-all" >All Price</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-1" value="price>0;price<100000" onClick={() => handleFilterPrice(0, 100000)} checked={priceFrom == 0 && priceTo == 100000} />
                        <label className="custom-control-label" htmlFor="price-1" >0 - 100.000 VND</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-2" value="price>100000;price<200000" onClick={() => handleFilterPrice(100000, 200000)} checked={priceFrom == 100000 && priceTo == 200000} />
                        <label className="custom-control-label" htmlFor="price-2" >100.000 - 200.000 VND</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-3" value="price>200000;price<400000" onClick={() => handleFilterPrice(200000, 400000)} checked={priceFrom == 200000 && priceTo == 400000} />
                        <label className="custom-control-label" htmlFor="price-3" >200.000 - 400.000</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-4" value="price>400000;price<500000" onClick={() => handleFilterPrice(400000, 500000)} checked={priceFrom == 400000 && priceTo == 500000} />
                        <label className="custom-control-label" htmlFor="price-4" >400.000 - 500.000 VND</label>
                    </div>
                </form>
            </div>
            {/* Price End */}

        </div>
    );
}

export default Sidebar