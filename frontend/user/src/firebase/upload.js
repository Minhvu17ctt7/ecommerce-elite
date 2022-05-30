import storage from './index';

const uploadImage = async (path, imageAsFile) => {
    const uploadTask = storage.ref(`/${path}/${imageAsFile.name}`);

    await uploadTask.put(imageAsFile);
    const url = await uploadTask.getDownloadURL();
    return url;
}

export default uploadImage;