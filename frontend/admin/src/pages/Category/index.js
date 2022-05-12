import classNames from 'classnames';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import React, { useEffect, useRef, useState } from 'react';
import uploadImage from '../../firebase/upload';
import categoryApi from '../../service/categoryService';
const Category = () => {
    let emptyProduct = {
        id: null,
        name: '',
        image: "",
        description: ''
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

    const forceFetchData = () => {
        setCountFetchDate(preState => preState + 1);
    }

    useEffect(() => {
        (async () => {

            const categoryResponse = await categoryApi.getAllCategory(false);

            setCategories(categoryResponse.data);

        })()
    }, [countFetchData]);

    const openNew = () => {
        setProduct(emptyProduct);
        setSubmitted(false);
        setProductDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setProductDialog(false);
    }

    const hideDeleteProductDialog = () => {
        setDeleteProductDialog(false);
    }

    const saveProduct = async () => {
        setSubmitted(true);

        if (product.name.trim()) {
            if (mainImage != null) {
                const urlImage = await uploadImage("/categories", mainImage);
                product['image'] = urlImage;
            }
            if (product.id) {
                await categoryApi.updateCategories(product);
                toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Category Updated', life: 3000 });
            }
            else {
                await categoryApi.createCategory(product);
                toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Category Created', life: 3000 });
            }

            setProductDialog(false);
            setProduct(emptyProduct);
            forceFetchData();
        }
    }

    const editProduct = (product) => {
        setProduct({ ...product });
        setProductDialog(true);
    }

    const confirmDeleteProduct = async (product) => {
        const availableDelete = await categoryApi.checkAvailableDeleteCategory(product.id);
        if (availableDelete.data.availableDelete) {
            setProduct(product);
            setDeleteProductDialog(true);
        } else {
            toast.current.show({ severity: 'warn', summary: `Can't delete category ${product.name}`, detail: `${product.name} has products`, life: 5000 });
        }
    }

    const deleteProduct = async () => {
        await categoryApi.deleteCategory(product.id);
        setDeleteProductDialog(false);
        setProduct(emptyProduct);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Category Deleted', life: 3000 });
        forceFetchData();

    }


    const confirmDeleteSelected = () => {
        setDeleteProductsDialog(true);
    }



    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _product = { ...product };
        _product[`${name}`] = val;

        setProduct(_product);
    }

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <div className="my-2">
                    <Button label="New" icon="pi pi-plus" className="p-button-success mr-2" onClick={openNew} />
                </div>
            </React.Fragment>
        )
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
                <img src={rowData.image} alt={rowData.image} className="shadow-2" width="100" />
            </>
        )
    }

    const actionBodyTemplate = (rowData) => {
        return (
            <div className="actions">
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editProduct(rowData)} />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-warning mt-2" onClick={() => confirmDeleteProduct(rowData)} />
            </div>
        );
    }

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Manage Categories</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );

    const productDialogFooter = (
        <>
            <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveProduct} />
        </>
    );
    const deleteProductDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteProductDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteProduct} />
        </>
    );


    const [mainImage, setMainImage] = useState(null);

    const handleChangeImage = (e) => {
        setMainImage(e.target.files[0]);
        const objectUrl = URL.createObjectURL(e.target.files[0])
        product['image'] = objectUrl;
    }

    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar>

                    <DataTable ref={dt} value={categories} selection={selectedProducts} onSelectionChange={(e) => setSelectedProducts(e.value)}
                        dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]} className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                        globalFilter={globalFilter} emptyMessage="No products found." header={header} responsiveLayout="scroll">
                        <Column field="Id" header="Id" sortable body={codeBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="name" header="Name" sortable body={nameBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column header="Image" body={imageBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column body={actionBodyTemplate}></Column>
                    </DataTable>

                    <Dialog visible={productDialog} style={{ width: '450px' }} header="Product Details" modal className="p-fluid" footer={productDialogFooter} onHide={hideDialog}>
                        {/* {product.image && <img src={product.image} alt={product.image} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />} */}
                        <img src={product.image ? product.image : "/images/image-default.jpg"} alt={product.image} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />
                        <div className="field">
                            <label htmlFor="name">Image</label>
                            <input type="file" id="mainImage" required onChange={handleChangeImage} className="custom-file-input" />
                            {submitted && !product.image && <small className="p-invalid">Image is required.</small>}
                        </div>
                        <div className="field">
                            <label htmlFor="name">Name</label>
                            <InputText id="name" value={product.name} onChange={(e) => { onInputChange(e, 'name') }} required autoFocus className={classNames({ 'p-invalid': submitted && !product.name })} />
                            {submitted && !product.name && <small className="p-invalid">Name is required.</small>}
                        </div>
                        <div className="field">
                            <label htmlFor="description">Description</label>
                            <InputTextarea id="description" value={product.description} onChange={(e) => onInputChange(e, 'description')} required rows={3} cols={20} />
                        </div>
                    </Dialog>

                    <Dialog visible={deleteProductDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteProductDialogFooter} onHide={hideDeleteProductDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {product && <span>Are you sure you want to delete <b>{product.name}</b>?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(Category, comparisonFn);