import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { InputText } from 'primereact/inputtext';
import { Rating } from 'primereact/rating';
import { Toast } from 'primereact/toast';
import React, { useEffect, useRef, useState } from 'react';
import categoryApi from '../../service/categoryService';
import ProductService from '../../service/ProductService';
import { convertTime } from '../../uitl/time';

const ProductDeleted = () => {
    let emptyProduct = {
        id: null,
        name: '',
        alias: '',
        mainImage: "https://cdn.ssstutter.com/products/nCRHI1bpbr1ZIsxG/042022/1650424149829.jpeg",
        shortDescription: '',
        fullDescription: '',
        categoryId: null,
        price: 0,
        quantity: 0,
        averageRating: 0
    };

    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [productDialog, setProductDialog] = useState(false);
    const [deleteProductDialog, setDeleteProductDialog] = useState(false);
    const [deleteProductsDialog, setDeleteProductsDialog] = useState(false);
    const [product, setProduct] = useState(emptyProduct);
    const [selectedProducts, setSelectedProducts] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const [countFetchData, setCountFetchDate] = useState(0);
    const toast = useRef(null);
    const dt = useRef(null);


    useEffect(() => {
        // const productService = new ProductService();
        // productService.getProducts().then(data => setProducts(data));
        (async () => {

            const categoryResponse = await categoryApi.getAllCategory(false);
            const productResponse = await ProductService.getAllProductFilter(true);

            setCategories(categoryResponse.data);
            setProducts(productResponse.data);

        })()
    }, []);

    console.log("products: ", product);

    const formatCurrency = (value) => {
        return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
    }



    const codeBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Code</span>
                {rowData.id}
            </>
        );
    }

    const nameBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {rowData.name}
            </>
        );
    }

    const imageBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Image</span>
                <img src={rowData.mainImage} alt={rowData.mainImage} className="shadow-2" width="100" />
            </>
        )
    }

    const priceBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Price</span>
                {formatCurrency(rowData.price)}
            </>
        );
    }

    const categoryBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Category</span>
                {rowData.category.name}
            </>
        );
    }

    const createdDateBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {convertTime(rowData.createdDate)}
            </>
        );
    }

    const updatedDateBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {convertTime(rowData.lastModifiedDate)}
            </>
        );
    }

    const ratingBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Reviews</span>
                <Rating value={rowData.averageRating} readonly cancel={false} />
            </>
        );
    }


    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Manage Products Deleted</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );


    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    {/* <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar> */}

                    <DataTable ref={dt} value={products} selection={selectedProducts} onSelectionChange={(e) => setSelectedProducts(e.value)}
                        dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]} className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                        globalFilter={globalFilter} emptyMessage="No products found." header={header} responsiveLayout="scroll">

                        <Column field="Id" header="Id" sortable body={codeBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="name" header="Name" sortable body={nameBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column header="Image" body={imageBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="price" header="Price" body={priceBodyTemplate} sortable headerStyle={{ width: '14%', minWidth: '8rem' }}></Column>
                        <Column field="category" header="Category" sortable body={categoryBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="createdDate" header="CreatedDate" sortable body={createdDateBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="updatedDate" header="UpdatedDate" sortable body={updatedDateBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>

                        <Column field="rating" header="Reviews" body={ratingBodyTemplate} sortable headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        {/* <Column field="inventoryStatus" header="Status" body={statusBodyTemplate} sortable headerStyle={{ width: '14%', minWidth: '10rem' }}></Column> */}
                    </DataTable>


                </div>
            </div>
        </div>
    );
}


export default React.memo(ProductDeleted);