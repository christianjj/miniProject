package com.example.androidtechnicalproject.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.androidtechnicalproject.R
import com.example.androidtechnicalproject.databinding.ListOfCategoryBinding
import com.example.androidtechnicalproject.localdatabase.CategoriesDao
import com.example.androidtechnicalproject.localdatabase.CategoryDatabase
import com.example.androidtechnicalproject.module.MealsAdapter
import com.example.androidtechnicalproject.module.MealsPresenter
import com.example.androidtechnicalproject.module.MealsCategoriesView
import com.example.androidtechnicalproject.model.MealsCategory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.rogomi.atlasfx.common.ProgressBarHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class CategoryListFragment : Fragment(), MealsCategoriesView {

    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mPresenter: MealsPresenter
    private lateinit var binding: ListOfCategoryBinding
    private lateinit var mAdapter: MealsAdapter
    private lateinit var dialog : BottomSheetDialog
    private lateinit var progressBarHandler: ProgressBarHandler
    private val existingName = listOf("christian", "chan")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoriesDao: CategoriesDao = CategoryDatabase.getDatabase(requireContext()).categoryDao()
        mPresenter = MealsPresenter(this, categoriesDao)
        progressBarHandler =ProgressBarHandler(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListOfCategoryBinding.inflate(inflater, container, false)
        mCategoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        mCategoryViewModel.readAllData.observe(
            viewLifecycleOwner
        ) { category -> mAdapter.initialize(category) }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        initAdapter()
        mPresenter.getCategories()
    }

    private fun initAction() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
             mPresenter.getCategories()
            }
            addCategory.setOnClickListener {
                showBottomSheet(Type.Add, null)

//                val intent = Intent(requireContext(), Newactivity::class.java)
//                startActivity(intent)

            }

        }


    }

    private fun insertDataToDatabase(mealsCategory: MealsCategory): Boolean {
        mCategoryViewModel.addCategory(mealsCategory)
        return true
    }

    private fun updateDataToDatabase(mealsCategory: MealsCategory) {
        mCategoryViewModel.updateCategory(mealsCategory)
        Toast.makeText(requireContext(), getString(R.string.updated_message), Toast.LENGTH_SHORT).show()
    }

    private fun deleteDataToDatabase(mealsCategory: MealsCategory) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mCategoryViewModel.deleteCategory(mealsCategory)
            Toast.makeText(requireContext(), getString(R.string.deleted_message), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }
        builder.setTitle(getString(R.string.dialog_ask_delete_title, mealsCategory.strCategory))
        builder.setMessage(getString(R.string.dialog_ask_delete_message, mealsCategory.strCategory))
        builder.create().show()

    }


    private fun initAdapter() {
        mAdapter =
            MealsAdapter(this)
        binding.rvList.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvList.adapter = mAdapter
    }

    override fun getLifecycleCoroutineScope(io: CoroutineDispatcher): CoroutineScope {
        return viewLifecycleOwner.lifecycleScope
    }

    override fun onGetMeals(data: List<MealsCategory>) {
        //Log.d("mealsREsponse", data.toString())
         binding.swipeRefreshLayout.isRefreshing = false
            var newdata  = mutableListOf<MealsCategory>()
            newdata.addAll(data)
            for (s in newdata) {
                insertDataToDatabase(MealsCategory(s.id,s.strCategory,s.strCategoryDescription,
                    System.currentTimeMillis(),s.strCategoryThumb))
            }

        //mAdapter.initialize(data)

    }


    override fun onError(message: String) {
      //  Log.d("error", "show Dialog Message $message")
    }

    override fun showLoading() {
        progressBarHandler.showProgressDialog()
    }

    override fun hideLoading() {
        progressBarHandler.hideProgressDialog()
    }

    override fun onUpdateData(data: MealsCategory) {
        showBottomSheet(Type.Update, data)
    }

    override fun onDeleteData(data: MealsCategory) {
        deleteDataToDatabase(data)

    }

    override fun onStop() {
        super.onStop()
        mPresenter.cancelFetch()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showBottomSheet(type: Type, data: MealsCategory?) {

        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_category, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val button = dialogView.findViewById<Button>(R.id.btnSubmit)
        val btnDelete = dialogView.findViewById<ImageView>(R.id.imgDelete)
        val name = dialogView.findViewById<TextInputEditText>(R.id.name)
        val description = dialogView.findViewById<TextInputEditText>(R.id.description)
        val link = dialogView.findViewById<TextInputEditText>(R.id.link)


        when (type) {
            Type.Add -> {
                button.text = getString(R.string.add)
                btnDelete.isVisible = false
            }

            Type.Update -> {
                button.text = getString(R.string.update)
                name.setText(data?.strCategory)
                description.setText(data?.strCategoryDescription)
                link.setText(data?.strCategoryThumb)
            }

        }

        btnDelete.setOnClickListener {
            deleteDataToDatabase(data!!)
        }
        button.setOnClickListener {
            if (checkInputs(name.text.toString(),description.text.toString(),link.text.toString())){
                if (type == Type.Add) {
                   if ( insertDataToDatabase(
                        MealsCategory(
                            0,
                            name.text.toString(),
                            description.text.toString(),
                            System.currentTimeMillis(),
                            link.text.toString()
                        )
                    )){
                       Toast.makeText(requireContext(), getString(R.string.added_message), Toast.LENGTH_SHORT).show()
                   }

                }
                if (type == Type.Update) {
                    updateDataToDatabase(
                        MealsCategory(
                            data!!.id,
                            name.text.toString(),
                            description.text.toString(),
                            System.currentTimeMillis(),
                            link.text.toString()
                        )
                    )
                }

                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.must_fill_up_all_the_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.setContentView(dialogView)
        dialog.show()
    }

    fun checkInputs(name: String?, description: String?, link: String?): Boolean {
        return name.toString().isNotEmpty() && description.toString()
            .isNotEmpty() && link.toString().isNotEmpty()
    }

    fun checkInputsIfNotEmpty(name: String?, description: String?, url: String?): Boolean {
        return name.toString().isNotEmpty() && description.toString().isNotEmpty() && url.toString().isNotEmpty()
    }
}

enum class Type {
    Add, Update
}